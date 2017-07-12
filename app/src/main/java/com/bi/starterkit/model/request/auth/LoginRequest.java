package com.bi.starterkit.model.request.auth;

import com.google.gson.Gson;
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

public class LoginRequest {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("device_id")
    private String deviceID;
    @SerializedName("device_name")
    private String deviceName;

    public LoginRequest() {
    }

    private LoginRequest(String email, String password, String deviceID, String deviceName) {
        this.email = email;
        this.password = password;
        this.deviceID = deviceID;
        this.deviceName = deviceName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getJson(LoginRequest request) {
        return new Gson().toJson(request, LoginRequest.class);
    }
}
