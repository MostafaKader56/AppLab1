package com.technoship.appLab1.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technoship.appLab1.R;
import com.technoship.appLab1.databinding.MapViewBinding;
import com.technoship.appLab1.databinding.QrCodeDialogBinding;
import com.technoship.appLab1.models.LabClient;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class Constants {
   public static int anInt = 0;

    // Normal IDs
    public static final String SHARED_PREF_ID = "SHARED_PREF_ID";
    public static final String FIRST_OPEN_ID_MAIN = "FIRST_OPEN_ID_MAIN";
    public static final String APPLIED_LANG_SUFFIX = "APPLIED_LANG_SUFFIX";
    public static final String TITLE_KEY_DETAILS_ACTIVITY = "TITLE_KEY_DETAILS_ACTIVITY";
    public static final String DETAILS_KEY_DETAILS_ACTIVITY = "DETAILS_KEY_DETAILS_ACTIVITY";
    public static final String IMAGE_LINK_KEY_DETAILS_ACTIVITY = "IMAGE_LINK_KEY_DETAILS_ACTIVITY";
    public static final String TITLE_KEY_ITEMS_ACTIVITY = "TITLE_KEY_ITEMS_ACTIVITY";
    public static final String ID_VIEW_KEY_ITEMS_ACTIVITY = "ID_VIEW_KEY_ITEMS_ACTIVITY";
    public static final String OFFER_ID_FOR_QR_CODE_DETAILS_ACTIVITY = "OFFER_ID_FOR_QR_CODE_DETAILS_ACTIVITY";

    public static final String NOTIFICATION_OBJECT_ID = "NOTIFICATION_OBJECT_ID";
    public static final String NOTIFICATION_KIND_CLOUD_FUNCTIONS = "notificationKind";
    public static final String NOTIFICATION_OBJECT = "NOTIFICATION_OBJECT";

    // Firestore IDs
    public static final String PUBLISHED_FIRESTORE = "published";
    public static final String BRANCHES_FIRESTORE = "Branches";
    public static final String PACKAGES_FIRESTORE = "Packages";
    public static final String OFFERS_FIRESTORE = "Offers";
    public static final String ANNOUNCEMENT_FIRESTORE = "Announcements";
    public static final String HEALTH_TIPS_FIRESTORE = "HealthTips";
    public static final String HOME_VISIT_FIRESTORE = "HomeVisit";
    public static final String RESULT_REQUEST_FIRESTORE = "ResultRequest";
    public static final String LAB_CLIENTS_FIRESTORE = "labClients";

    public static void createMapDialog(Context context, double v, double v1, String label){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        MapViewBinding promptView = MapViewBinding.inflate(LayoutInflater.from(context), null, false);
        alertDialog.setView(promptView.getRoot());

        MapView mMapView = promptView.mapView;
        MapsInitializer.initialize(context);

        mMapView.onCreate(alertDialog.onSaveInstanceState());
        mMapView.onResume();

        mMapView.getMapAsync(googleMap -> {
            LatLng markerLatLang = new LatLng(v, v1);
            googleMap.addMarker(new MarkerOptions().position(markerLatLang).title(label));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerLatLang));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        });

        alertDialog.show();
    }
    public static void createQrCodeDialog(Context context, Bitmap bitmap){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        QrCodeDialogBinding promptView = QrCodeDialogBinding.inflate(LayoutInflater.from(context), null, false);
        alertDialog.setView(promptView.getRoot());

        promptView.qrCode.setImageBitmap(bitmap);

        alertDialog.show();
    }

    public static String dateForFileStorageReference() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(new Date());
    }

    public static void unExpectedError(Activity context, Exception e) {
//        FirebaseCrashlytics cathartics = FirebaseCrashlytics.getInstance();
//        if (e != null){
//            cathartics.recordException(new Exception("Manually Reported Error: "+e.toString()));
//            Log.wtf("asdasdasd", e.toString());
//        }
//        else {
//            cathartics.log("Null Manually Reported Error");
//            Log.wtf("asdasdasd", "NULL");
//        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context)
                .setTitle("")
                .setMessage("")
                .setPositiveButton("", (dialog, which) -> dialog.dismiss());
        builder.create();
        builder.show();
    }

    public static boolean isVailPhoneNumber(String number){
        String expression = "^[+][0-9]{10,13}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static boolean isGPS_TurnedOn(Activity context) {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

//        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//        return provider.contains("gps") || provider.contains("network");

//        final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
//        String allowedLocationProviders =
//                Settings.System.getString(context.getContentResolver(),
//                        Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
////        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//        if (allowedLocationProviders == null) {
//            allowedLocationProviders = "";
//        }
//        return allowedLocationProviders.contains(LocationManager.GPS_PROVIDER);
//        return manager.isProviderEnabled( LocationManager.GPS_PROVIDER );
    }

    public static String dateForId(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ssSSS_", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Random random = new Random();
        String alphabet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        StringBuilder string2append = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            string2append.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        String dateTime = dateFormat.format(new Date());
        return dateTime+string2append;
    }

    public static String dateForIdToOfferId(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Random random = new Random();
        String alphabet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        StringBuilder string2append = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            string2append.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        String dateTime = dateFormat.format(new Date());
        return dateTime+string2append;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void openAppOnPlayStore(Context context){
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static ArrayList<String> setCurrentTime() {
        ArrayList<String> time = new ArrayList<>();
        time.add(TimeZone.getDefault().getID());
        Calendar calendar = Calendar.getInstance();
        time.add(String.valueOf(calendar.get(Calendar.YEAR)));
        if (calendar.get(Calendar.MONTH) > 9){
            time.add(String.valueOf(calendar.get(Calendar.MONTH)));
        }
        else {
            time.add("0"+calendar.get(Calendar.MONTH));
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) > 9){
            time.add(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        }
        else {
            time.add("0"+calendar.get(Calendar.DAY_OF_MONTH));
        }
        if (calendar.get(Calendar.HOUR) > 9){
            time.add(String.valueOf(calendar.get(Calendar.HOUR)));
        }
        else {
            time.add("0"+calendar.get(Calendar.HOUR));
        }
        if (calendar.get(Calendar.MINUTE) > 9){
            time.add(String.valueOf(calendar.get(Calendar.MINUTE)));
        }
        else {
            time.add("0"+calendar.get(Calendar.MINUTE));
        }
        time.add(String.valueOf(calendar.get(Calendar.AM_PM)));
        return time;
    }

    public static String getCurrentTime(Resources res, ArrayList<String> time) {
//        String currentZoneId = TimeZone.getDefault().getID();
//        // TODO: this Operation will be delayed to next update
//        if ( currentZoneId.equals( time.get(0) ) )
//        {
//        if (Calendar.getInstance().get(Calendar.YEAR) == Integer.parseInt(time.get(1))){
        return time.get(3)+" "+res.getStringArray(R.array.Months)[Integer.parseInt(time.get(2))] +
                " "+time.get(4)+":"+time.get(5)+" "+
                res.getStringArray(R.array.AmPm)[Integer.parseInt(time.get(6))];
//        }
//        else {
//            return time.get(3)+" "+ res.getStringArray(R.array.Months)[Integer.parseInt(time.get(2))]
//                    +" "+ time.get(1)+" "+time.get(4)+":"+time.get(5)+" "+
//                    res.getStringArray(R.array.AmPm)[Integer.parseInt(time.get(6))];
//        }
//        }
//        else {
//            TimeZone LocalTimeZone = TimeZone.getTimeZone(currentZoneId);
//            int newDayInMonth, newDayInWeek, newMonth, newAmPm;
//            String newTime;
//            if (Calendar.getInstance().get(Calendar.YEAR) == Integer.parseInt(time.get(1))) {
//                SimpleDateFormat format = new SimpleDateFormat("ddMMhh:mma", Locale.ENGLISH);
//
//                format.setTimeZone(TimeZone.getTimeZone(time.get(0)));
//
//                Date parsedFormat;
//
//                String in;
//                try {
//                    if (Integer.parseInt(time.get(6)) == Calendar.PM){
//                        in = time.get(3)+time.get(2)+time.get(4)+":"
//                                +time.get(5)+res.getStringArray(R.array.AmPm1)[1];
//                    }
//                    else {
//                        in = time.get(3)+time.get(2)+time.get(4)+":"
//                                +time.get(5)+time.get(5)+res.getStringArray(R.array.AmPm1)[0];
//                    }
//                    parsedFormat = format.parse(in);
//                } catch (ParseException e) {
//                    return e.toString();
//                }
//
//                format.setTimeZone(LocalTimeZone);
//                String out = format.format(parsedFormat);
//                if (out.substring(9).equals(res.getStringArray(R.array.AmPm1)[0])){
//                    newAmPm = 0;
//                } else {
//                    newAmPm = 1;
//                }
//                newTime = out.substring(4, 9);
//                newMonth = Integer.parseInt( out.substring(2, 4) );
//                newDayInMonth = Integer.parseInt( out.substring(0, 2) );
//                return newDayInMonth+" "+
//                        res.getStringArray(R.array.Months)[newMonth] +" "+ newTime+" "+
//                        res.getStringArray(R.array.AmPm)[newAmPm];
//            }
//            else {
//                int newYear;
//            }
//            return "";
//        }
    }

    public static final String LAB_CLIENT_ID_IN_SHARED_PREF = "LAB_CLIENT_ID_IN_SHARED_PREF";
    public static final String USED_OFFERS_ID_IN_SHARED_PREF = "USED_OFFERS_ID_IN_SHARED_PREF";

    public static void saveLabClientInSharedPreference(Context context, LabClient labClient){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SHARED_PREFS_1", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(labClient.getUsedOffersIds());
        editor.putString(LAB_CLIENT_ID_IN_SHARED_PREF, labClient.getUserId());
        editor.putString(USED_OFFERS_ID_IN_SHARED_PREF, json);
        editor.apply();
    }

    public static LabClient loadLabClientInSharedPreference(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SHARED_PREFS_1", MODE_PRIVATE);
        Gson gson = new Gson();
        String labClientId = sharedPreferences.getString(LAB_CLIENT_ID_IN_SHARED_PREF, null);
        String json = sharedPreferences.getString(USED_OFFERS_ID_IN_SHARED_PREF, null);
        Type type = new TypeToken<ArrayList<LabClient>>() {}.getType();
        ArrayList<String> labClientModelArrayList = gson.fromJson(json, type);
        if (labClientModelArrayList == null) { labClientModelArrayList = new ArrayList<>(); }
        return new LabClient(labClientId, labClientModelArrayList);
    }

    public static String getLabClientId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SHARED_PREFS_1", MODE_PRIVATE);
        return  sharedPreferences.getString(LAB_CLIENT_ID_IN_SHARED_PREF, null);
    }

    public static ArrayList<String> getLabClientUsedOffers(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SHARED_PREFS_1", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(USED_OFFERS_ID_IN_SHARED_PREF, null);
        Type type = new TypeToken<ArrayList<LabClient>>() {}.getType();
        ArrayList<String> labClientModelArrayList = gson.fromJson(json, type);
        if (labClientModelArrayList == null) { labClientModelArrayList = new ArrayList<>(); }
        return labClientModelArrayList;
    }

    public static boolean viewAppInfoActivity(Context context) {
        return !context.getSharedPreferences("SHARED_PREFS_1", MODE_PRIVATE).getBoolean("viewAppInfoActivity" ,false);
    }

    public static boolean isSliderSkied(Context context){
        return !context.getSharedPreferences("SHARED_PREFS_1", MODE_PRIVATE).getBoolean("isSliderSkied_1" ,false);
    }

    public static void setSliderSkied(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SHARED_PREFS_1", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isSliderSkied_1", true);
        editor.apply();
    }
}
