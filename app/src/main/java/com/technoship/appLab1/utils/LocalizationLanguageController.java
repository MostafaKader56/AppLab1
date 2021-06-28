package com.technoship.appLab1.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.os.ConfigurationCompat;

public class LocalizationLanguageController{


    private final OnLocalizationChanged onLocalizationChanged;
    private SharedPreferences.Editor editor;
    private static SharedPreferences sp;
    private final Activity context;
    private boolean isFirstTime;


    public LocalizationLanguageController(Activity context,
                                          OnLocalizationChanged onLocalizationChanged) {
        this.onLocalizationChanged = onLocalizationChanged;
        this.context = context;
        doIt();
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public interface OnLocalizationChanged {
        void onChanged(String language);
    }

    private void doIt() {
        sp = context.getSharedPreferences(Constants.SHARED_PREF_ID, Activity.MODE_PRIVATE);
        isFirstTime = sp.getBoolean(Constants.FIRST_OPEN_ID_MAIN, true);
    }

    public void doFirstTimeProcess(){
        editor = sp.edit();
        editor.putBoolean(Constants.FIRST_OPEN_ID_MAIN, false);
        editor.apply();

        String currentSuffix;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) { currentSuffix = getAndroidDefaultLocal(); }
        else currentSuffix = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0).getLanguage();

        setLocale(currentSuffix);
        saveLangPreferences(currentSuffix);

        editor.putString(Constants.APPLIED_LANG_SUFFIX, currentSuffix);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getAndroidDefaultLocal(){
        return Resources.getSystem().getConfiguration().getLocales().get(0).getLanguage();
    }

    public void changeLanguageBetweenArabicAndEnglish(){
        if (sp.getString(Constants.APPLIED_LANG_SUFFIX, "ar").equals("ar")){
            setLocale("en");
            saveLangPreferences("en");
        }
        else {
            setLocale("ar");
            saveLangPreferences("ar");
        }
    }

    public static boolean isArabic(){
        return sp.getString(Constants.APPLIED_LANG_SUFFIX, "ar").equals("ar");
    }

    private void saveLangPreferences(String lang){
        editor = sp.edit();
        editor.putString(Constants.APPLIED_LANG_SUFFIX, lang);
        editor.apply();
    }

    public void setLocale(String language) {
        onLocalizationChanged.onChanged(language);
    }
}
