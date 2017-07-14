package com.bi.starterkit.service;

import android.util.Log;

import com.bi.starterkit.data.Constants;
import com.bi.starterkit.model.request.auth.ForgotPasswordRequest;
import com.bi.starterkit.model.request.auth.LoginRequest;
import com.bi.starterkit.model.request.auth.RegisterRequest;
import com.bi.starterkit.model.request.auth.SocialLoginRequest;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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

public class TaskService extends OkhttpTaskService {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String CONTENT_APP_JSON = "application/json";

    public static final int REQ_LOGIN = 1;
    public static final int REQ_SOCIAL_LOGIN = 2;
    public static final int REQ_REGISTER = 3;
    public static final int REQ_LOGOUT = 4;
    public static final int REQ_FORGOT_PASSWORD = 5;

    public void login(LoginRequest request) {
        requestType = REQ_LOGIN;
        requestURL = Constants.URL_LOGIN;
        Headers headers = new Headers.Builder()
                .set("Content-Type", CONTENT_APP_JSON)
                .build();
        String json = request.getJson(request);
        RequestBody requestBody = RequestBody.create(JSON, json);
        addToRequestQueue(OkhttpTaskService.POST, requestURL, requestType, requestBody, headers);
    }

    public void socialLogin(SocialLoginRequest request) {
        requestType = REQ_SOCIAL_LOGIN;
        requestURL = Constants.URL_SOCIAL_LOGIN;
        Headers headers = new Headers.Builder()
                .set("Content-Type", CONTENT_APP_JSON)
                .build();
        String json = request.getJson(request);
        RequestBody requestBody = RequestBody.create(JSON, json);
        addToRequestQueue(OkhttpTaskService.POST, requestURL, requestType, requestBody, headers);
    }

    public void register(RegisterRequest request) {
        requestType = REQ_REGISTER;
        requestURL = Constants.URL_REGISTER;
        Headers headers = new Headers.Builder()
                .set("Content-Type", CONTENT_APP_JSON)
                .build();
        String json = request.getJson(request);
        RequestBody requestBody = RequestBody.create(JSON, json);
        addToRequestQueue(OkhttpTaskService.POST, requestURL, requestType, requestBody, headers);
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        requestType = REQ_FORGOT_PASSWORD;
        requestURL = Constants.URL_FORGOT_PASSWORD;
        Headers headers = new Headers.Builder()
                .set("Content-Type", CONTENT_APP_JSON)
                .build();
        String json = request.getJson(request);
        RequestBody requestBody = RequestBody.create(JSON, json);
        addToRequestQueue(OkhttpTaskService.POST, requestURL, requestType, requestBody, headers);
    }

    public void logout() {
        requestType = REQ_LOGOUT;
        requestURL = Constants.URL_LOGOUT;
        Headers headers = new Headers.Builder()
                .set("Authorization", "")
                .build();
        RequestBody requestBody = RequestBody.create(null, new byte[0]);
        addToRequestQueue(OkhttpTaskService.POST, requestURL, requestType, requestBody, headers);
    }
}
