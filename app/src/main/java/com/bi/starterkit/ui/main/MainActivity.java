package com.bi.starterkit.ui.main;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bi.starterkit.R;
import com.bi.starterkit.controller.RealmManager;
import com.bi.starterkit.model.realm.User;
import com.bi.starterkit.ui.BaseActivity;
import com.bumptech.glide.Glide;

import io.realm.Realm;

public class MainActivity extends BaseActivity {

    private Realm realm;
    private TextView sampleText;
    private AppCompatImageView sampleImage;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmManager.incrementCount();
        realm = RealmManager.getRealm();
        user = realm.where(User.class).findFirst();

        initializeView();
    }

    private void initializeView() {
        sampleText = (TextView) findViewById(R.id.sample_text);
        sampleImage = (AppCompatImageView) findViewById(R.id.sample_image);

        sampleText.setText(user.getEmail() + "\n" +
                user.getName() + "\n" + user.getPhoto());
        Glide.with(this).load(user.getPhoto()).into(sampleImage);
    }


    @Override
    public void receive(int type, boolean success, Bundle extras) {

    }
}
