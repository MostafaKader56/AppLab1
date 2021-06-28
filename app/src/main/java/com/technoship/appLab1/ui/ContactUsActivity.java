package com.technoship.appLab1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.technoship.appLab1.utils.Constants;
import com.technoship.appLab1.R;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


    }

    public void facebookClicked(View view) {
        Uri uri = Uri.parse("https://www.facebook.com/LABALHAYAT");
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://page/1726156937612187");
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void mailClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: labalhayat@gmail.com"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void googlePlayClicked(View view) {
        Constants.openAppOnPlayStore(this);
    }
}