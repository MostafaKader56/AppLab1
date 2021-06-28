package com.technoship.appLab1.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.technoship.appLab1.R;
import com.technoship.appLab1.adapter.SliderAdapter;
import com.technoship.appLab1.databinding.ActivitySliderBinding;
import com.technoship.appLab1.models.LabClient;
import com.technoship.appLab1.utils.Constants;
import com.technoship.appLab1.utils.LocalizationLanguageController;

public class SliderActivity extends LocalizationActivity {
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private int[] layouts;
    private LabClient labClient;
    private Button nextBtn, skipBtn;
    private boolean isLoadLabClientCreated;
    private LocalizationLanguageController localizationLanguageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySliderBinding binding = ActivitySliderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Constants.anInt == 0){
            Constants.anInt++;
        }
        else {
            finish();
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = binding.viewPager;
        linearLayout = binding.dotsLayout;
        Button langButton;
        if (LocalizationLanguageController.isArabic()){
            nextBtn = binding.sliderButtonLeft;
            skipBtn = binding.sliderButtonRight;
            langButton = binding.langButton1;
        }
        else {
            nextBtn = binding.sliderButtonRight;
            skipBtn = binding.sliderButtonLeft;
            langButton = binding.langButton2;
        }

        langButton.setText(getString(R.string.main_menu_langText));
        langButton.setOnClickListener(view -> {
            Constants.anInt = 0;
            localizationLanguageController.changeLanguageBetweenArabicAndEnglish();
        });

        nextBtn.setText(getString(R.string.Slider_next));
        skipBtn.setText(getString(R.string.Slider_skip));

        nextBtn.setOnClickListener(view -> nextButtonClicked());
        skipBtn.setOnClickListener(view -> closeSlider());

        layouts = new int[] {R.layout.slide_1, R.layout.slide_2, R.layout.slide_3, R.layout.slide_4, R.layout.slide_5, R.layout.slide_6};
        SliderAdapter sliderAdapter = new SliderAdapter(layouts, SliderActivity.this);

        viewPager.setAdapter(sliderAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == layouts.length-1){
                    nextBtn.setText(R.string.Slider_start);
                    skipBtn.setVisibility(View.GONE);
                }
                else {
                    nextBtn.setText(getString(R.string.Slider_next));
                    skipBtn.setVisibility(View.VISIBLE);
                }
                if (LocalizationLanguageController.isArabic()){
                    setDots(layouts.length-1-position);
                }
                else setDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (LocalizationLanguageController.isArabic()) setDots(layouts.length-1);
        else setDots(0);

        onOpen();
        localizationLanguageController = new LocalizationLanguageController(this,  this::setLanguage);
    }

    private void onOpen() {
        if (Constants.getLabClientId(SliderActivity.this) != null){
            isLoadLabClientCreated = true;
            return;
        }
        String android_id = getPhoneId();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(Constants.LAB_CLIENTS_FIRESTORE)
                .document(android_id);

        labClient = new LabClient(android_id);

        documentReference.get()
                .addOnSuccessListener(this, documentSnapshot -> {
                    if(documentSnapshot !=null && documentSnapshot.exists()){
                        labClient = documentSnapshot.toObject(LabClient.class);
                        Constants.saveLabClientInSharedPreference(SliderActivity.this, labClient);
                        isLoadLabClientCreated = true;
                    }
                    else {
                        documentReference.set(labClient).addOnSuccessListener(this, aVoid ->{
                            Constants.saveLabClientInSharedPreference(SliderActivity.this, labClient);
                            isLoadLabClientCreated = true;
                        });
                    }
                });
    }

    private String getPhoneId(){
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private void setDots(int page){
        linearLayout.removeAllViews();
        TextView[] dotsTv = new TextView[layouts.length];
        for (int i = 0; i < dotsTv.length; i++){
            dotsTv[i] = new TextView(this);
            dotsTv[i].setText(Html.fromHtml("&#8226;"));
            dotsTv[i].setTextSize(30);
            dotsTv[i].setTextColor(Color.parseColor("#a9b4bb"));
            linearLayout.addView(dotsTv[i]);
        }

        if (dotsTv.length > 0){
            dotsTv[page].setTextColor(Color.parseColor("#ffffff"));
        }
    }

    private void nextButtonClicked() {
        int currentPage = viewPager.getCurrentItem()+1;
        if (currentPage < layouts.length){
            viewPager.setCurrentItem(currentPage);
        }
        else {
            closeSlider();
        }
    }

    private void closeSlider(){
        if (isLoadLabClientCreated){
            Constants.setSliderSkied(SliderActivity.this);
            startActivity(new Intent(SliderActivity.this, MainActivity.class));
            finish();
        }
        else {
            ProgressDialog progressDialog = new ProgressDialog(SliderActivity.this);
            progressDialog.setMessage(getString(R.string.opening));
            progressDialog.show();

            new Handler().postDelayed(() -> {
                if (isLoadLabClientCreated){
                    Constants.setSliderSkied(SliderActivity.this);
                    startActivity(new Intent(SliderActivity.this, MainActivity.class));
                    finish();
                }
                else {
                    createdLabClientNotFinished();
                    progressDialog.dismiss();
                }
            }, 3500);
        }
    }

    private void createdLabClientNotFinished(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.sliderActivity_alertTitle);
        builder.setMessage(R.string.sliderActivity_alertMessage);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> onBackPressed());
        builder.setOnDismissListener(dialogInterface -> onBackPressed());
        builder.create();
        builder.show();
    }
}