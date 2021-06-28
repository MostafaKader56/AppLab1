package com.technoship.appLab1.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.technoship.appLab1.R;
import com.technoship.appLab1.adapter.AboutViewPagerAdapter;
import com.technoship.appLab1.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.technoship.appLab1.databinding.ActivityAboutBinding binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = binding.appBar;
        setSupportActionBar(toolbar);

        TabLayout tabLayout = binding.tabLayout;
        ViewPager viewPager = binding.viewPager;

        int[] layouts = new int[]{R.layout.slide_about_1, R.layout.slide_about_2, R.layout.slide_about_3, R.layout.slide_about_4, R.layout.slide_about_5, R.layout.slide_about_6};
        String[] titles = new String[]{getString(R.string.about_tag_whoseWe), getString(R.string.about_tag_ourSight), getString(R.string.about_tag_ourMessage), getString(R.string.about_tag_ourPrevilges), getString(R.string.about_tag_agreements), getString(R.string.about_tag_salamtkProgram)};
        AboutViewPagerAdapter sliderAdapter = new AboutViewPagerAdapter(layouts, titles, this);

        viewPager.setAdapter(sliderAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}