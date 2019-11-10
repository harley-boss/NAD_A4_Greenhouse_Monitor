/**
 * Project: NAD A4
 * File: MainActivity.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Main class of android app. Sets up tabs and views, etc.
 */

package com.example.greenhousemonitor;

import android.content.Intent;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.greenhousemonitor.Tabs.TabAccount;
import com.example.greenhousemonitor.Tabs.TabGreenhouse;
import com.example.greenhousemonitor.Tabs.TabSensors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private boolean greenhousePicked;
    private FloatingActionButton fab;
    private SectionsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        setNotificationBarColor();
        loginUser();
    }

    private void setNotificationBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.edit_line));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CANCELED) {
            finish();
            System.exit(0);
        } else if (resultCode == RESULT_OK) {
            if (requestCode == LoginActivity.REQUEST_CODE) {
                User user = data.getParcelableExtra("user");
                getMainApplication().setUser(user);
                UiHelper.showToast(this, "Successfully logged in");
                setupViewPager();
                setupTabIcons();
            }
        }
    }

    private MainApplication getMainApplication() {
        Application application = getApplication();
        return (MainApplication)application;
    }


    private void loginUser() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, LoginActivity.REQUEST_CODE);
    }

    private void setupViewPager() {
        mViewPager = findViewById(R.id.view_pager);
        setupViewPager(mViewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.edit_line));
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_organization);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_sensor);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_user);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TabGreenhouse());
        adapter.addFrag(new TabSensors());
        adapter.addFrag(new TabAccount());
        viewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void setGreenhousePicked(boolean hasBeenPicked) {
        greenhousePicked = hasBeenPicked;
    }

    public boolean hasGreenhouseBeenPicked() {
        return greenhousePicked;
    }

    @Override
    public void onClick(View v) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           switch(position) {
               case 0:
                   return new TabGreenhouse();
               case 1:
                   return new TabSensors();
               case 2:
                   return new TabAccount();
               default:
                   return null;
           }
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}