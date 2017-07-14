package com.bi.starterkit.data;

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

public class Constants {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private static native String getAndroidGoogleClient();
    private static native String getBaseUrl();


    public static final String BASE_URL = getBaseUrl();
    public static final String ANDROID_GOOGLE_CLIENT = getAndroidGoogleClient();
    public static final String URL_REGISTER = BASE_URL + "/register";
    public static final String URL_LOGIN = BASE_URL + "/login";
    public static final String URL_LOGOUT = BASE_URL + "/logout";
    public static final String URL_SOCIAL_LOGIN = URL_LOGIN + "/social_media";
    public static final String URL_FORGOT_PASSWORD = BASE_URL + "/forgot_password";
}
