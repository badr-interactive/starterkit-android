package com.bi.starterkit.ui.bottomnav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bi.starterkit.R;
import com.bi.starterkit.ui.BaseActivity;
import com.bi.starterkit.ui.home.HomeFragment;
import com.bi.starterkit.ui.other.OtherFragment;

/**
 * starterkit-android
 * <p>
 * This source code is part of intellectual property rights of PT. Badr Interactive.
 * Any use of this source code without permission from PT. Badr Interactive is prohibitted
 * and violated the company policy. Any violation will be dealt in accordance with the
 * existing mechanism in the company.
 * <p>
 * Source code ini merupakan bagian dari hak kekayaan intelektual PT. Badr Interactive.
 * Tidak diizinkan untuk menggunakan source code ini tanpa seizin PT. Badr Interactive.
 * Setiap pelanggaran yang dilakukan akan ditindak dengan mekanisme yang berlaku di perusahaan.
 * <p>
 * Created by Roland on 9/18/2017.
 */

public class BottomNavigationActivity extends BaseActivity implements AHBottomNavigation.OnTabSelectedListener {

    private static final String KEY_ACTIVE_NAV = "active_nav";
    public static final int NAVIGATION_HOME = 0;
    public static final int NAVIGATION_OTHER = 1;
    private int activeNavigation = NAVIGATION_HOME;

    Toolbar toolbar;
    AHBottomNavigation bottomNavigationView;

    Fragment activeFragment;
    HomeFragment homeFragment;
    OtherFragment otherFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        initialize();
    }

    private void initialize() {
        homeFragment = HomeFragment.newInstance();
        otherFragment = OtherFragment.newInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initializeBottomNav();
        bottomNavigationView.setCurrentItem(activeNavigation);
    }

    private void initializeBottomNav() {
        AHBottomNavigationItem home = new AHBottomNavigationItem(getString(R.string.nav_home), R.drawable.ic_lock);
        AHBottomNavigationItem other = new AHBottomNavigationItem(getString(R.string.nav_other), R.drawable.ic_lock);

        bottomNavigationView.addItem(home);
        bottomNavigationView.addItem(other);

        bottomNavigationView.setAccentColor(ContextCompat.getColor(this, R.color.colorPrimary));
        bottomNavigationView.setInactiveColor(ContextCompat.getColor(this, R.color.grey));
        bottomNavigationView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigationView.setOnTabSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            activeNavigation = savedInstanceState.getInt(KEY_ACTIVE_NAV);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_ACTIVE_NAV, activeNavigation);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {

    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        onSelectedNavigation(position);
        return true;
    }

    public void showHomeFragment() {
        activeNavigation = NAVIGATION_HOME;
        if (homeFragment != null) {
            activeFragment = homeFragment;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, activeFragment, HomeFragment.TAG)
                    .commitAllowingStateLoss();
        }
    }

    public void showOtherFragment() {
        activeNavigation = NAVIGATION_OTHER;
        if (otherFragment != null) {
            activeFragment = otherFragment;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, activeFragment, OtherFragment.TAG)
                    .commitAllowingStateLoss();
        }
    }


    private void onSelectedNavigation(int position) {
        switch (position) {
            case NAVIGATION_HOME:
                showHomeFragment();
                break;
            case NAVIGATION_OTHER:
                showOtherFragment();
                break;
        }
    }
}
