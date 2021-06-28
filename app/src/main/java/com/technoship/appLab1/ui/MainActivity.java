package com.technoship.appLab1.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.technoship.appLab1.utils.Constants;
import com.technoship.appLab1.R;
import com.technoship.appLab1.databinding.ActivityMainBinding;
import com.technoship.appLab1.utils.LocalizationLanguageController;

public class MainActivity extends LocalizationActivity {

    private LocalizationLanguageController localizationLanguageController;
    private ActivityResultLauncher<Intent> activityResultLauncherHomeActivityVisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle(getString(R.string.app_name));

        Bundle bundle1 = getIntent().getExtras();
        if (bundle1 != null && bundle1.containsKey(Constants.NOTIFICATION_KIND_CLOUD_FUNCTIONS)){
            if (bundle1.getInt(Constants.NOTIFICATION_KIND_CLOUD_FUNCTIONS) == 4){
                bundle1.putString(Constants.TITLE_KEY_ITEMS_ACTIVITY, getString(R.string.main_view_tag_branches));
                bundle1.putInt(Constants.ID_VIEW_KEY_ITEMS_ACTIVITY, 2);
                startActivity(new Intent(MainActivity.this, ItemsActivity.class).putExtras(bundle1));
            }
            else startActivity(new Intent(MainActivity.this, DetailsActivity.class).putExtras(bundle1));
        }

        Bundle bundle = new Bundle();

        activityResultLauncherHomeActivityVisit = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        boolean done = (boolean) data.getExtras().get("DONE");
                        if (done){
                            new AlertDialog.Builder(this)
                                    .setTitle(R.string.requestSent_title)
                                    .setMessage(R.string.requestSent_description)
                                    .setPositiveButton(R.string.ok, (dialog, id) -> { })
                                    .create()
                                    .show();
                        }
                    }
                });

        binding.homeTagCardView.setOnClickListener(view -> activityResultLauncherHomeActivityVisit.launch(new Intent(MainActivity.this, HomeVisitActivity.class)));
        binding.resultsTagCardView.setOnClickListener(view -> activityResultLauncherHomeActivityVisit.launch(new Intent(MainActivity.this, ResultActivity.class)));
        binding.offersTagCardView.setOnClickListener(view -> {
            bundle.putString(Constants.TITLE_KEY_ITEMS_ACTIVITY, getString(R.string.main_view_tag_offers));
            bundle.putInt(Constants.ID_VIEW_KEY_ITEMS_ACTIVITY, 1);
            startActivity(new Intent(MainActivity.this, ItemsActivity.class).putExtras(bundle));
        });
        binding.branchesTagCardView.setOnClickListener(view -> {
            bundle.putString(Constants.TITLE_KEY_ITEMS_ACTIVITY, getString(R.string.main_view_tag_branches));
            bundle.putInt(Constants.ID_VIEW_KEY_ITEMS_ACTIVITY, 2);
            startActivity(new Intent(MainActivity.this, ItemsActivity.class).putExtras(bundle));
        });
        binding.packagesTagCardView.setOnClickListener(view -> {
            bundle.putString(Constants.TITLE_KEY_ITEMS_ACTIVITY, getString(R.string.main_view_tag_packages));
            bundle.putInt(Constants.ID_VIEW_KEY_ITEMS_ACTIVITY, 4);
            startActivity(new Intent(MainActivity.this, ItemsActivity.class).putExtras(bundle));
        });
        binding.healthTipsTagCardView.setOnClickListener(view -> {
            bundle.putString(Constants.TITLE_KEY_ITEMS_ACTIVITY, getString(R.string.main_view_tag_healthTips));
            bundle.putInt(Constants.ID_VIEW_KEY_ITEMS_ACTIVITY, 3);
            startActivity(new Intent(MainActivity.this, ItemsActivity.class).putExtras(bundle));
        });
        binding.examinationPrecautionsTagCardView.setOnClickListener(view -> {
            bundle.putString(Constants.TITLE_KEY_ITEMS_ACTIVITY, getString(R.string.main_view_tag_examinationPrecautions));
            bundle.putInt(Constants.ID_VIEW_KEY_ITEMS_ACTIVITY, 6);
            startActivity(new Intent(MainActivity.this, ItemsActivity.class).putExtras(bundle));
        });
        binding.announcementTagCardView.setOnClickListener(view -> {
            bundle.putString(Constants.TITLE_KEY_ITEMS_ACTIVITY, getString(R.string.main_view_tag_announcement));
            bundle.putInt(Constants.ID_VIEW_KEY_ITEMS_ACTIVITY, 5);
            startActivity(new Intent(MainActivity.this, ItemsActivity.class).putExtras(bundle));
        });
        binding.contactUsTagCardView.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ContactUsActivity.class)));
        binding.aboutUsTagCardView.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AboutActivity.class)));

        localizationLanguageController = new LocalizationLanguageController(this,  this::setLanguage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (Constants.viewAppInfoActivity(MainActivity.this)){
            menu.removeItem(R.id.main_menu_appInfo);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.main_menu_phone) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+201094975895"));
            startActivity(intent);
        } else if (itemId == R.id.main_menu_appInfo) {
            startActivity(new Intent(MainActivity.this, AboutAppActivity.class));
        } else if (itemId == R.id.main_menu_lang) {
            changeLanguageClicked();
        }
        return true;
    }

    private void changeLanguageClicked() {
        localizationLanguageController.changeLanguageBetweenArabicAndEnglish();
    }
}
