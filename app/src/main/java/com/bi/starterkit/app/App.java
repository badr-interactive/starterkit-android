package com.bi.starterkit.app;

import android.app.Application;
import android.os.Bundle;

import com.bi.starterkit.controller.RealmManager;
import com.bi.starterkit.data.PreferenceManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.realm.Realm;

/**
 * android-starterkit
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
 * Created by Roland on 7/4/2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmManager.initializeRealmConfig();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        PreferenceManager.getInstance(getApplicationContext());

        FirebaseAnalytics.getInstance(getApplicationContext());
    }
}
