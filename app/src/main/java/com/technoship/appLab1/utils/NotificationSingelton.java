package com.technoship.appLab1.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.technoship.appLab1.R;

import java.util.concurrent.atomic.AtomicInteger;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationSingelton {
    private static NotificationSingelton notificationSingelton;
    private static Context context;

    private final AtomicInteger c = new AtomicInteger(0);
    public int getID() {
        return c.incrementAndGet();
    }

    private NotificationSingelton(Context mContext) {
        context = mContext;
    }

    public static NotificationSingelton getInstance(Context mContext) {
        if (notificationSingelton == null) {
            notificationSingelton = new NotificationSingelton(mContext);
        }

        return notificationSingelton;
    }

    public void sendMeNotification(String message, String title, PendingIntent pendingIntent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notification = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Talbatk", "Talbatk", importance);
            channel.setDescription("It's a App channel");
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            manager.createNotificationChannel(channel);
            notification = new NotificationCompat.Builder(context, channel.getId());
        } else {
            notification = new NotificationCompat.Builder(context, "Talbatk");
        }

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification
//                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .setLights(Color.RED, 200, 200);

        manager.notify(getID(), notification.build());
    }

    public void sendMeNotification(String message, String title, PendingIntent pendingIntent, Uri ringtone, String chanelId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notification = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(chanelId, chanelId, importance);
            channel.setDescription("It's a Talbatk channel");
            channel.enableVibration(true);
            channel.enableLights(true);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(ringtone, attributes);
            channel.setLightColor(Color.RED);
            manager.createNotificationChannel(channel);
            notification = new NotificationCompat.Builder(context, channel.getId());
        } else {
            notification = new NotificationCompat.Builder(context, "Talbatk");
        }

//        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification
//                .setDefaults(Notification.DEFAULT_ALL)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(ringtone)
                .setLights(Color.RED, 200, 200);

        manager.notify(getID(), notification.build());
    }
    public void sendMeNotification(String message, String title, PendingIntent pendingIntent, String chanelId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notification = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(chanelId, chanelId, importance);
            channel.setDescription("It's a Talbatk channel");
            channel.enableVibration(true);
            channel.enableLights(true);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
//            channel.setSound(ringtone, attributes);
            channel.setLightColor(Color.RED);
            manager.createNotificationChannel(channel);
            notification = new NotificationCompat.Builder(context, channel.getId());
        } else {
            notification = new NotificationCompat.Builder(context, "Talbatk");
        }

//        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification
//                .setDefaults(Notification.DEFAULT_ALL)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
//                .setSound(ringtone)
                .setLights(Color.RED, 200, 200);

        manager.notify(getID(), notification.build());
    }
//    public void ASKOSodg() {
//        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//        NotificationCompat.Builder notification = null;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("Talbatk", "Talbatk", importance);
//            channel.setDescription("It's a Talbatk channel");
//            channel.enableVibration(false);
//            channel.enableLights(true);
//            channel.setLightColor(Color.RED);
//            manager.createNotificationChannel(channel);
//            notification = new NotificationCompat.Builder(context, channel.getId());
//        } else {
//            notification = new NotificationCompat.Builder(context, "Talbatk");
//        }
//
//        notification.setNumber(4);
//        notification.setDefaults(Notification.DEFAULT_ALL);
//        notification.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
//
//
//        manager.notify(getID(), notification.build());
//    }
}