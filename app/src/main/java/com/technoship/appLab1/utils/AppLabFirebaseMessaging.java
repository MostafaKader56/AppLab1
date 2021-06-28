package com.technoship.appLab1.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.technoship.appLab1.R;
import com.technoship.appLab1.ui.Splash;

import java.util.Map;
import java.util.Objects;

public class AppLabFirebaseMessaging extends FirebaseMessagingService {
    private static final String LOG_TAG = AppLabFirebaseMessaging.class.getSimpleName();
    @Override
    public void onNewToken(@NonNull String token) {
        Log.wtf(LOG_TAG, "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        final Context context = this;
        final Map<String, String> map = remoteMessage.getData();
        final int kind =Integer.parseInt(Objects.requireNonNull(map.get("notificationKind")));
        final Bundle mBundle = new Bundle();

        mBundle.putInt(Constants.NOTIFICATION_KIND_CLOUD_FUNCTIONS, kind);

        String titleOfNewObject;
        if (LocalizationLanguageController.isArabic()){
            titleOfNewObject = map.get("titleAr");
        }
        else {
            titleOfNewObject = map.get("title");
        }
        String notificationObjectId = map.get("notificationObjectId");
        mBundle.putString(Constants.NOTIFICATION_OBJECT_ID, notificationObjectId);
        Intent resultIntent1 = new Intent(context, Splash.class)
                .putExtras( mBundle );
        TaskStackBuilder stackBuilder1 = TaskStackBuilder.create(context);
        stackBuilder1.addNextIntentWithParentStack(resultIntent1);
        PendingIntent resultPendingIntent1 = stackBuilder1.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        switch (kind){
            case 1:
                // offer
                NotificationSingelton.getInstance(context).sendMeNotification(
                        titleOfNewObject,
                        getResources().getStringArray(R.array.notificationTitle)[kind-1],
                        resultPendingIntent1,
                        "channel one");
                break;
            case 2:
                // package
                NotificationSingelton.getInstance(context).sendMeNotification(
                        titleOfNewObject,
                        getResources().getStringArray(R.array.notificationTitle)[kind-1],
                        resultPendingIntent1,
                        "channel two");
                break;
            case 3:
                // health tips
                NotificationSingelton.getInstance(context).sendMeNotification(
                        titleOfNewObject,
                        getResources().getStringArray(R.array.notificationTitle)[kind-1],
                        resultPendingIntent1,
                        "channel three");

                break;
            case 4:
                // branches
                NotificationSingelton.getInstance(context).sendMeNotification(
                        titleOfNewObject,
                        getResources().getStringArray(R.array.notificationTitle)[kind-1],
                        resultPendingIntent1,
                        "channel four");

                break;
            case 5:
                // announcement
                NotificationSingelton.getInstance(context).sendMeNotification(
                        titleOfNewObject,
                        getResources().getStringArray(R.array.notificationTitle)[kind-1],
                        resultPendingIntent1,
                        "channel five");
                break;
        }
        super.onMessageReceived(remoteMessage);
    }

    private void sendRegistrationToServer(String token) {

    }
}
