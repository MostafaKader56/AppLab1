package com.technoship.appLab1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.technoship.appLab1.R;
import com.technoship.appLab1.utils.Constants;
import com.technoship.appLab1.databinding.ActivityAboutAppBinding;

public class AboutAppActivity extends AppCompatActivity {

    private ActivityAboutAppBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = String.format(getString(R.string.version), pInfo.versionName);
            binding.versionTextView.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void googlePlayClicked(View view) {
        Constants.openAppOnPlayStore(this);
    }
}
