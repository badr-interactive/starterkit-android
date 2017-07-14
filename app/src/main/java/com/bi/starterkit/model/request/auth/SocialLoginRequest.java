package com.bi.starterkit.model.request.auth;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

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
 * Created by Roland on 7/6/2017.
 */

public class SocialLoginRequest {
    public enum AUTH_MODE {
        FACEBOOK,
        GOOGLE
    }
    private static AUTH_MODE mode = AUTH_MODE.FACEBOOK;
    @SerializedName("app")
    private String app;
    @SerializedName("email")
    private String email;
    @SerializedName("token")
    private String token;
    @SerializedName("device_id")
    private String deviceID;
    @SerializedName("device_name")
    private String deviceName;
    @SerializedName("fcm_token")
    private String fcmToken;

    public static AUTH_MODE getMode() {
        return mode;
    }

    public static void setMode(AUTH_MODE mode) {
        SocialLoginRequest.mode = mode;
    }

    public String getApp() {
        return app;
    }

    public void setApp() {
        if (mode == AUTH_MODE.FACEBOOK) {
            this.app = "facebook";
        } else {
            this.app = "google";
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getJson(SocialLoginRequest request) {
        return new Gson().toJson(request, SocialLoginRequest.class);
    }
}
