package com.bi.starterkit.controller;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObject;

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
 * Created by Roland on 7/5/2017.
 */

public class RealmManager {
    static Realm realm;

    static RealmConfiguration realmConfiguration;
    private static int activityCount = 0;

    private RealmManager() {

    }

    public static void initializeRealmConfig() {
        if (realmConfiguration == null) {
            setRealmConfiguration(new RealmConfiguration.Builder()
                    .name("starterkit.realm")
                    .deleteRealmIfMigrationNeeded()
                    .build());
        }
    }

    public static void setRealmConfiguration(RealmConfiguration realmConfiguration) {
        RealmManager.realmConfiguration = realmConfiguration;
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static Realm getRealm() {
        return realm;
    }

    public static void incrementCount() {
        if (activityCount == 0) {
            if (realm != null) {
                if (!realm.isClosed()) {
                    realm.close();
                    realm = null;
                }
            }
            realm = Realm.getDefaultInstance();
        }
        activityCount++;
    }

    public static void decrementCount() {
        activityCount--;
        if (activityCount <= 0) {
            activityCount = 0;
            realm.close();
            Realm.compactRealm(realmConfiguration);
            realm = null;
        }
    }

    public static void createOrUpdateObjectFromJson(Realm realm, final Class clazz, final String json) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateObjectFromJson(clazz, json);
            }
        });
    }

    public static void createOrUpdateAllFromJson(Realm realm, final Class clazz, final String json) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(clazz, json);
            }
        });
    }
}

