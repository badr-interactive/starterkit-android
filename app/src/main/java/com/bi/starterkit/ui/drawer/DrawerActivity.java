package com.bi.starterkit.ui.drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bi.starterkit.R;
import com.bi.starterkit.controller.RealmManager;
import com.bi.starterkit.data.Constants;
import com.bi.starterkit.data.PreferenceManager;
import com.bi.starterkit.model.realm.User;
import com.bi.starterkit.ui.BaseActivity;
import com.bi.starterkit.ui.authentication.BasicLoginActivity;
import com.bi.starterkit.ui.bottomnav.BottomNavigationActivity;
import com.bi.starterkit.ui.home.HomeFragment;
import com.bi.starterkit.ui.other.OtherFragment;
import com.bi.starterkit.ui.profile.ProfileActivity;
import com.bi.starterkit.ui.search.SearchActivity;
import com.bi.starterkit.ui.share.ShareFragment;
import com.bi.starterkit.utils.view.CircleImageView;
import com.bumptech.glide.Glide;

import io.realm.Realm;

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

public class DrawerActivity extends BaseActivity implements View.OnClickListener {
    private static final String KEY_ACTIVE_NAV = "active_nav";

    Realm realm;
    ActionBarDrawerToggle drawerToggle;

    Toolbar toolbar;
    FrameLayout drawerHeader;
    FrameLayout container;
    DrawerLayout drawerLayout;
    CircleImageView ivProfile;
    LinearLayout navigationHome, navigationOther, navigationShare;
    TextView navigationHelp, navigationTos, navigationToa, navigationAbout, navigationLogout,
            tvName, tvEmail;

    NavigationItem itemCurrent, itemHome, itemOther, itemBottomNav;

    Fragment activeFragment;
    HomeFragment homeFragment;
    OtherFragment otherFragment;
    ShareFragment shareFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        RealmManager.incrementCount();
        realm = RealmManager.getRealm();

        initialize();

        if (activeFragment == null) {
            initializeHome();
        }
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        container = (FrameLayout) findViewById(R.id.container);
        drawerHeader = (FrameLayout) findViewById(R.id.drawer_header);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ivProfile = (CircleImageView) findViewById(R.id.iv_profile);
        navigationHome = (LinearLayout) findViewById(R.id.nav_home);
        navigationOther = (LinearLayout) findViewById(R.id.nav_other);
        navigationShare = (LinearLayout) findViewById(R.id.nav_share);
        navigationHelp = (TextView) findViewById(R.id.nav_help);
        navigationTos = (TextView) findViewById(R.id.nav_tos);
        navigationToa = (TextView) findViewById(R.id.nav_toa);
        navigationAbout = (TextView) findViewById(R.id.nav_about);
        navigationLogout = (TextView) findViewById(R.id.nav_logout);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvEmail = (TextView) findViewById(R.id.tv_email);

        drawerHeader.setOnClickListener(this);
        navigationHome.setOnClickListener(this);
        navigationOther.setOnClickListener(this);
        navigationShare.setOnClickListener(this);
        navigationHelp.setOnClickListener(this);
        navigationTos.setOnClickListener(this);
        navigationToa.setOnClickListener(this);
        navigationAbout.setOnClickListener(this);
        navigationLogout.setOnClickListener(this);

        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        itemHome = new NavigationItem(navigationHome, getString(R.string.nav_home), R.drawable.ic_lock, this);
        itemOther = new NavigationItem(navigationOther, getResources().getString(R.string.nav_other), R.drawable.ic_lock, this);
        itemBottomNav = new NavigationItem(navigationShare, getResources().getString(R.string.nav_bottom_navigation), R.drawable.ic_mail, this);


        User user = realm.copyFromRealm(realm.where(User.class).findFirst());
        Glide.with(this).load(user.getPhoto()).placeholder(R.drawable.ic_lock).error(R.drawable.ic_lock).dontAnimate().into(ivProfile);
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void switchNavigation(NavigationItem navItem) {
        if (itemCurrent != null) {
            itemCurrent.setInactive();
        }
        itemCurrent = navItem;
        itemCurrent.setActive();
    }

    private void switchToActiveFragment() {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, activeFragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeHome() {
        homeFragment = HomeFragment.newInstance();
        activeFragment = homeFragment;
        switchToActiveFragment();
        switchNavigation(itemHome);
    }

    private void initializeOther() {
        otherFragment = OtherFragment.newInstance();
        activeFragment = otherFragment;
        switchToActiveFragment();
        switchNavigation(itemOther);
    }

    private void initializeShare() {
        shareFragment = ShareFragment.newInstance();
        activeFragment = shareFragment;
        switchToActiveFragment();
        switchNavigation(itemBottomNav);
    }

    private void closeDrawer() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drawer_header:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.nav_home:
                initializeHome();
                closeDrawer();
                break;
            case R.id.nav_share:
                startActivity(new Intent(this, BottomNavigationActivity.class));
                closeDrawer();
                break;
            case R.id.nav_other:
                initializeOther();
                closeDrawer();
                break;
            case R.id.nav_help:
                break;
            case R.id.nav_toa:
                break;
            case R.id.nav_tos:
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_logout:
                PreferenceManager.getInstance().clearPreferences();
                startActivity(new Intent(this, BasicLoginActivity.class));
                finish();
                break;
        }
    }
}
