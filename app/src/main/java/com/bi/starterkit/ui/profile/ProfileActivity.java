package com.bi.starterkit.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bi.starterkit.R;
import com.bi.starterkit.controller.RealmManager;
import com.bi.starterkit.model.realm.User;
import com.bi.starterkit.ui.BaseActivity;
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
 * Created by Roland on 9/11/2017.
 */

public class ProfileActivity extends BaseActivity {

    Realm realm;
    User user;

    CircleImageView ivImage;
    TextView tvName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        RealmManager.incrementCount();
        realm = RealmManager.getRealm();
        user = realm.copyFromRealm(realm.where(User.class).findFirst());

        initialize();

    }

    private void initialize() {
        ivImage = (CircleImageView) findViewById(R.id.iv_image);
        tvName = (TextView) findViewById(R.id.tv_name);

        Glide.with(this).load(user.getPhoto())
                .error(R.drawable.ic_lock)
                .placeholder(R.drawable.ic_lock)
                .centerCrop()
                .dontAnimate()
                .into(ivImage);
        tvName.setText(user.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RealmManager.decrementCount();
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {

    }
}
