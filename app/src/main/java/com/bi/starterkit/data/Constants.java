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

    private static native String getCertificateKey();

    private static native String getComodoKey();

    private static native String getSaltKey();

    public static final String BASE_URL = getBaseUrl();
    public static final String CERTIFICATE_KEY = getCertificateKey();
    public static final String COMODO_KEY = getComodoKey();
    public static final String ANDROID_GOOGLE_CLIENT = getAndroidGoogleClient();
    public static final String SALT_KEY = getSaltKey();
    public static final String URL_REGISTER = BASE_URL + "/auth/register";
    public static final String URL_LOGIN = BASE_URL + "/auth/login";
    public static final String URL_LOGOUT = BASE_URL + "/auth/logout";
    public static final String URL_SOCIAL_LOGIN = BASE_URL + "/auth/social_login";
    public static final String URL_FORGOT_PASSWORD = BASE_URL + "/auth/forgot_password";
    public static final String URL_RESET_PASSWORD = BASE_URL + "/auth/reset_password";


    //preference
    public static final String LOGGED_IN = "logged_in";
}
