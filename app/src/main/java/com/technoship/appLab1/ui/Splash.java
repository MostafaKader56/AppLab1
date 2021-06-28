package com.technoship.appLab1.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.airbnb.lottie.LottieAnimationView;
import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.technoship.appLab1.R;
import com.technoship.appLab1.databinding.ActivitySplashBinding;
import com.technoship.appLab1.models.Announcement;
import com.technoship.appLab1.models.Branch;
import com.technoship.appLab1.models.LoadedHealthTip;
import com.technoship.appLab1.models.Offer;
import com.technoship.appLab1.models.Package;
import com.technoship.appLab1.utils.Constants;
import com.technoship.appLab1.utils.LocalizationLanguageController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Splash extends LocalizationActivity {

    private ActivitySplashBinding binding;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private boolean isOpening, appIsRooted; //, remoteConfigError;
    private SharedPreferences sharedPreferences;
    private Bundle bundle;
    private LottieAnimationView progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        doItTest();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        LocalizationLanguageController languageController = new LocalizationLanguageController(this,  this::setLanguage);
        if (languageController.isFirstTime()){
            languageController.doFirstTimeProcess();
        }

        Animation zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        ImageView img = binding.img;
        progressBar = binding.progressBar;
        img.startAnimation(zoom);
        new Handler().postDelayed(() -> binding.img1.setVisibility(View.VISIBLE), 1000);
        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.VISIBLE);

            if (appIsRooted) {
                showAlertDialogAndExitApp("This device is rooted. You can't use this app.");
                return;
            }

            new Handler().postDelayed(this::start, 1500);

        }, 2500);
    }

    private void start() {
        bundle = getIntent().getExtras();

        sharedPreferences = getSharedPreferences("SHARED_PREFS_1", MODE_PRIVATE);

        Map<String, Object> defaultsRate = new HashMap<>();
        defaultsRate.put("mustUpdateVersions", "");
        defaultsRate.put("ask4UpdateVersions", "");
        defaultsRate.put("viewAppInfoActivity", "0");

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(14400) // change to 14400 on published app
                .build();

        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(defaultsRate);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isOpening) {
                Toast.makeText(Splash.this, getString(R.string.splash_poorConnectionTip), Toast.LENGTH_SHORT).show();
                workByOldSavedData();
            }
//            else if (remoteConfigError){
//                unExpectedErrorTryAgainLater();
//            }
        }, 4500);

        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(task -> {
            if (isOpening) return;

            if (task.isSuccessful()) {
                isOpening = true;

                final String mustUpdateVersionsString = mFirebaseRemoteConfig.getString("mustUpdateVersions");
                final String ask4UpdateVersionsString = mFirebaseRemoteConfig.getString("ask4UpdateVersions");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("mustUpdateVersionsString", mustUpdateVersionsString);
                editor.putString("ask4UpdateVersionsString", ask4UpdateVersionsString);
                editor.putBoolean("viewAppInfoActivity", Integer.parseInt(mFirebaseRemoteConfig.getString("viewAppInfoActivity")) == 1);
                editor.apply();

                final String[] mustUpdateVersions = mustUpdateVersionsString.split(":");
                final String[] ask4UpdateVersions = ask4UpdateVersionsString.split(":");

                ArrayList<String> mustUpdateVersionsList = new ArrayList<>(Arrays.asList(mustUpdateVersions));
                ArrayList<String> ask4UpdateVersionsList = new ArrayList<>(Arrays.asList(ask4UpdateVersions));

                if(mustUpdateVersionsList.contains(String.valueOf(getVersionCode()))){
                    showUpdateDialog(true);
                }
                else if(ask4UpdateVersionsList.contains(String.valueOf(getVersionCode()))){
                    showUpdateDialog(false);
                }
                else {
                    startProcess();
                }
            }
            else {
                Log.w("ASDASD", task.getException().toString());
//                remoteConfigError = true;
            }
        });
    }

    private void workByOldSavedData() {
        isOpening = true;
        String mustUpdateVersionsString = sharedPreferences.getString("mustUpdateVersionsString", "--");
        String ask4UpdateVersionsString = sharedPreferences.getString("ask4UpdateVersionsString", "--");

        if (mustUpdateVersionsString.equals("--")){
            ask4connectToNetwork();
            return;
        }

        String[] mustUpdateVersions = mustUpdateVersionsString.split(":");
        String[] ask4UpdateVersions = ask4UpdateVersionsString.split(":");

        ArrayList<String> mustUpdateVersionsList = new ArrayList<>(Arrays.asList(mustUpdateVersions));
        ArrayList<String> ask4UpdateVersionsList = new ArrayList<>(Arrays.asList(ask4UpdateVersions));

        if(mustUpdateVersionsList.contains(String.valueOf(getVersionCode()))){
            showUpdateDialog(true);
        }
        else if(ask4UpdateVersionsList.contains(String.valueOf(getVersionCode()))){
            showUpdateDialog(false);
        }
        else {
            startProcess();
        }
    }

    private void ask4connectToNetwork() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(R.string.splashActivity_ask4network_message);
        builder.setTitle(R.string.splashActivity_ask4network_title);
        builder.setNegativeButton(R.string.splashActivity_Exit, (dialog, which) -> {
            onBackPressed();
            finish();
        });
        builder.create();
        builder.show();
    }

    private void unExpectedErrorTryAgainLater() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.splashActivity_unExpectedError_title);
        builder.setMessage(R.string.splashActivity_unExpectedError_message);
        builder.setNegativeButton(R.string.splashActivity_Exit, (dialog, which) -> {
            onBackPressed();
            finish();
        });
        builder.create();
        builder.show();
    }

    private void showUpdateDialog(boolean extremist) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.splashActivity_updateDialogTitle);
        builder.setMessage(R.string.splashActivity_updateDialogContent);
        builder.setPositiveButton(R.string.splashActivity_update, (dialog, which) -> {
            Constants.openAppOnPlayStore(Splash.this);
            finish();
        });
        if (extremist){
            builder.setNegativeButton(R.string.splashActivity_Exit, (dialog, which) -> {
                onBackPressed();
                finish();
            });
        }
        else {
            builder.setNegativeButton(R.string.splashActivity_discard, (dialog, which) -> startProcess());
        }
        builder.create();
        builder.show();
    }

    private void startProcess(){
        if (Constants.isSliderSkied(Splash.this)){
            new Handler().postDelayed(() -> {
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(Splash.this, SliderActivity.class));
                finish();
            }, 3000);
        }
        else if (bundle != null && bundle.containsKey(Constants.NOTIFICATION_KIND_CLOUD_FUNCTIONS)){
            switch (bundle.getInt(Constants.NOTIFICATION_KIND_CLOUD_FUNCTIONS)){
                case 1:
                    // offer
                    FirebaseFirestore.getInstance().collection(Constants.OFFERS_FIRESTORE).document(bundle.getString(Constants.NOTIFICATION_OBJECT_ID)).get()
                            .addOnSuccessListener(Splash.this, documentSnapshot -> {
                                if(documentSnapshot !=null && documentSnapshot.exists()){
                                    Offer offer = documentSnapshot.toObject(Offer.class);
                                    bundle.putParcelable(Constants.NOTIFICATION_OBJECT, offer);
                                    if (LocalizationLanguageController.isArabic()){
                                        bundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, offer.getTitleAr());
                                        bundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, offer.getDetailsAr());
                                    }
                                    else {
                                        bundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, offer.getTitle());
                                        bundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, offer.getDetails());
                                    }
                                    if (offer.isOneTimeUse()){
                                        bundle.putString(Constants.OFFER_ID_FOR_QR_CODE_DETAILS_ACTIVITY, offer.getOfferId());
                                    }
                                    bundle.putString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY, offer.getImage());
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(Splash.this, MainActivity.class).putExtras(bundle));
                                    finish();
                                 }
                                else {
                                    unExpectedErrorTryAgainLater();
                                }
                            });
                    break;
                case 2:
                    // package
                    FirebaseFirestore.getInstance().collection(Constants.PACKAGES_FIRESTORE).document(bundle.getString(Constants.NOTIFICATION_OBJECT_ID)).get()
                            .addOnSuccessListener(Splash.this, documentSnapshot -> {
                                if(documentSnapshot !=null && documentSnapshot.exists()){
                                    Package aPackage = documentSnapshot.toObject(Package.class);
                                    bundle.putParcelable(Constants.NOTIFICATION_OBJECT, aPackage);
                                    if (LocalizationLanguageController.isArabic()){
                                        bundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, aPackage.getTitleAr());
                                        bundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, aPackage.getDetailsAr());
                                    }
                                    else {
                                        bundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, aPackage.getTitle());
                                        bundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, aPackage.getDetails());
                                    }
                                    bundle.putString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY, aPackage.getImage());
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(Splash.this, MainActivity.class).putExtras(bundle));
                                    finish();
                                }
                                else {
                                    unExpectedErrorTryAgainLater();
                                }
                            });
                    break;
                case 3:
                    // health Tip
                    FirebaseFirestore.getInstance().collection(Constants.HEALTH_TIPS_FIRESTORE).document(bundle.getString(Constants.NOTIFICATION_OBJECT_ID)).get()
                            .addOnSuccessListener(Splash.this, documentSnapshot -> {
                                if(documentSnapshot !=null && documentSnapshot.exists()){
                                    LoadedHealthTip healthTip = documentSnapshot.toObject(LoadedHealthTip.class);
                                    bundle.putParcelable(Constants.NOTIFICATION_OBJECT, healthTip);
                                    if (LocalizationLanguageController.isArabic()){
                                        bundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, healthTip.getTitleAr());
                                        bundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, healthTip.getDetailsAr());
                                    }
                                    else {
                                        bundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, healthTip.getTitle());
                                        bundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, healthTip.getDetails());
                                    }
                                    bundle.putString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY, healthTip.getImage());
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(Splash.this, MainActivity.class).putExtras(bundle));
                                    finish();
                                }
                                else {
                                    unExpectedErrorTryAgainLater();
                                }
                            });
                    break;
                case 4:
                    // branch
                    FirebaseFirestore.getInstance().collection(Constants.BRANCHES_FIRESTORE).document(bundle.getString(Constants.NOTIFICATION_OBJECT_ID)).get()
                            .addOnSuccessListener(Splash.this, documentSnapshot -> {
                                if(documentSnapshot !=null && documentSnapshot.exists()){
                                    Branch branch = documentSnapshot.toObject(Branch.class);
                                    bundle.putParcelable(Constants.NOTIFICATION_OBJECT, branch);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(Splash.this, MainActivity.class).putExtras(bundle));
                                    finish();
                                }
                                else {
                                    unExpectedErrorTryAgainLater();
                                }
                            });
                    break;
                case 5:
                    // announcement
                    FirebaseFirestore.getInstance().collection(Constants.ANNOUNCEMENT_FIRESTORE).document(bundle.getString(Constants.NOTIFICATION_OBJECT_ID)).get()
                            .addOnSuccessListener(Splash.this, documentSnapshot -> {
                                if(documentSnapshot !=null && documentSnapshot.exists()){
                                    Announcement announcement = documentSnapshot.toObject(Announcement.class);
                                    bundle.putParcelable(Constants.NOTIFICATION_OBJECT, announcement);
                                    if (LocalizationLanguageController.isArabic()){
                                        bundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, announcement.getTitleAr());
                                        bundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, announcement.getDetailsAr());
                                    }
                                    else {
                                        bundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, announcement.getTitle());
                                        bundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, announcement.getDetails());
                                    }
                                    bundle.putString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY, announcement.getImage());
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(Splash.this, MainActivity.class).putExtras(bundle));
                                    finish();
                                }
                                else {
                                    unExpectedErrorTryAgainLater();
                                }
                            });
                    break;
            }
        }
        else {
            new Handler().postDelayed(() -> {
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }, 1000);
        }
    }

    private int getVersionCode(){
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e){
            Log.wtf("asd", "NameNotFoundException: "+e.getMessage());
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return (int) pInfo.getLongVersionCode();
        } else {
            //noinspection deprecation
            return pInfo.versionCode;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(new DeviceUtils().isDeviceRooted()){
            appIsRooted = true;
        }
    }

    public void showAlertDialogAndExitApp(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                    finish();
                });

        alertDialog.show();
    }

    private class DeviceUtils {

        public Boolean isDeviceRooted(){
            return isRooted1();
//            || isrooted2();
        }

        private boolean isRooted1() {
            File file = new File("/system/app/Superuser.apk");
            return file.exists();
        }
    }

    private void doItTest() {
        
    }
}
