package com.bi.starterkit.data;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.securepreferences.SecurePreferences;
import com.tozny.crypto.android.AesCbcWithIntegrity;

import java.security.GeneralSecurityException;


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

public class PreferenceManager {
    private static final String TAG = PreferenceManager.class.getSimpleName();
    private static PreferenceManager instance;
    private SecurePreferences securePreferences;

    private PreferenceManager(Context context) {
        securePreferences = new SecurePreferences(context, Constants.SALT_KEY, context.getPackageName() + ".xml");
    }

    public static PreferenceManager getInstance(Context context) {
        if (instance == null) {
            synchronized (PreferenceManager.class) {
                if (instance == null) {
                    instance = new PreferenceManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public static PreferenceManager getInstance() {
        return instance;
    }

    public String getString(String key) {
        return securePreferences.getString(key, null);
    }

    public void storeString(String key, String value) {
        securePreferences.edit().putString(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return securePreferences.getBoolean(key, false);
    }

    public void storeBoolean(String key, boolean value) {
        securePreferences.edit().putBoolean(key, value).commit();
    }

    public boolean changePreferencePassword(String password, Context context) {
        if (securePreferences != null) {
            try {
                securePreferences.handlePasswordChange(password, context);
                return true;
            } catch (GeneralSecurityException e) {
                Log.e(TAG, "Error during password change", e);
            }
        }
        return false;
    }

    public void clearPreferences() {
        securePreferences.edit().clear().commit();
    }
}
