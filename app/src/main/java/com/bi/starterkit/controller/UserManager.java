package com.bi.starterkit.controller;

import com.facebook.login.LoginManager;

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
 * Created by Roland on 7/5/2017.
 */

public class UserManager {
    // Supported authentication mode
    public enum AUTH_MODE {
        PASSWORD,
        FACEBOOK,
        GOOGLE
    }
    private static AUTH_MODE mode = AUTH_MODE.PASSWORD; // default

    public static void setAuthMode(AUTH_MODE m) {
        mode = m;
    }

    public static void logoutActiveUser() {
        switch (mode) {
            case PASSWORD: {
                // Do nothing, handled by the `User.currentUser().logout();`
                break;
            }
            case FACEBOOK: {
                LoginManager.getInstance().logOut();
                break;
            }
            case GOOGLE: {
                // the connection is handled by `enableAutoManage` mode
                break;
            }
        }
//        SyncUser.currentUser().logout();
    }

    // Configure Realm for the current active user
//    public static void setActiveUser(SyncUser user) {
//        SyncConfiguration defaultConfig = new SyncConfiguration.Builder(user, RealmTasksApplication.REALM_URL).build();
//        Realm.setDefaultConfiguration(defaultConfig);
//    }
}

