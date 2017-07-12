package com.bi.starterkit.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bi.starterkit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;

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

public class OkhttpTaskService extends Service {
    public static final String RESPONSE_BODY = "response";
    public static final String RESPONSE_MESSAGE = "message";
    public static final String RESPONSE_STATUS = "success";
    public static final String RESPONSE_DATA = "data";
    public static final String GET = "GET";
    public static final String POST = "POST";
    private static final String TAG = OkhttpTaskService.class.getSimpleName();
    private static final int RESPONSE_SUCCESS = 2;
    private static final int CONNECTION_TIMEOUT_SECOND = 60;
    private static final int CONNECTION_READ_TIMEOUT_SECOND = 60;
    private static final int CONNECTION_WRITE_TIMEOUT_SECOND = 10;
    private final IBinder myBinder = new MyBinder();
    public int requestType;
    public String requestURL;
    // bound activity
    private Callback activityCallback;
    private OkHttpClient okHttpClient;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.BIND_AUTO_CREATE;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public void registerCallback(Callback activityCallback) {
        this.activityCallback = activityCallback;
    }

    void addToRequestQueue(String method, String url, int reqType, RequestBody requestBody, Headers headers) {
        Request request;
        if (headers != null) {
            request = new okhttp3.Request.Builder()
                    .url(url)
                    .method(method, method.equals(GET) ? null : requestBody)
                    .headers(headers)
                    .build();
        } else {
            request = new okhttp3.Request.Builder()
                    .url(url)
                    .method(method, method.equals(GET) ? null : requestBody)
                    .build();
        }

        if (isNetworkAvailable()) {
            if (okHttpClient == null) {
                okHttpClient = getOkHttpClient();
                call(request, reqType);
            } else {
                call(request, reqType);
            }
        } else {
            noInternetCallBack();
        }
    }

    private void call(Request request, final int reqType) {
        Log.d(TAG, "call() called with: request = [" + request + "], tag = [" + reqType + "]");
        final Bundle result = new Bundle();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                String responseBody = getString(R.string.no_response_server);
                result.putString(RESPONSE_BODY, responseBody);
                result.putString(RESPONSE_MESSAGE, responseBody);
                result.putString(RESPONSE_DATA, responseBody);
                sentCallback(reqType, false, result);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseString = response.body().string();
                switch (Integer.parseInt(Integer.toString(response.code()).substring(0, 1))) {
                    case RESPONSE_SUCCESS:
                        try {
                            Log.d("success listener type", reqType + " " + responseString);
                            result.putString(RESPONSE_BODY, responseString);
                            JSONObject responseJson = new JSONObject(responseString);
                            result.putBoolean(RESPONSE_STATUS, responseJson.getBoolean(RESPONSE_STATUS));
                            result.putString(RESPONSE_MESSAGE, responseJson.getString(RESPONSE_MESSAGE));
                            if (responseJson.has(RESPONSE_DATA)) {
                                Log.d("data", responseJson.get(RESPONSE_DATA).toString());
                                result.putString(RESPONSE_DATA, responseJson.get(RESPONSE_DATA).toString());
                            }
                            sentCallback(reqType, response.isSuccessful(), result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        try {
                            Log.d("failed listener type", reqType + " " + responseString);
                            result.putString(RESPONSE_BODY, responseString);
                            JSONObject responseJson = new JSONObject(responseString);
                            result.putBoolean(RESPONSE_STATUS, responseJson.getBoolean(RESPONSE_STATUS));
                            result.putString(RESPONSE_MESSAGE, responseJson.getString(RESPONSE_MESSAGE));
                            if (responseJson.has(RESPONSE_DATA)) {
                                result.putString(RESPONSE_DATA, responseJson.get(RESPONSE_DATA).toString());
                            }
                            sentCallback(reqType, false, result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
    }

    @NonNull
    private OkHttpClient getOkHttpClient() {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();

        return new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT_SECOND, TimeUnit.SECONDS)
                //use it when you need to implement specification of SSL
//                .connectionSpecs(Collections.singletonList(spec))
                //use this when you use certificate
//                .certificatePinner(new CertificatePinner.Builder()
//                        .add(CHANGE_WITH_WEB_ADDRESS, YOUR_CERTIFICATE_KEY)
//                        .build())
                .readTimeout(CONNECTION_READ_TIMEOUT_SECOND, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS)
                .build();
    }

    private void runOnUiThread(Runnable task) {
        new Handler(Looper.getMainLooper()).post(task);
    }

    void sentCallback(final int type, final boolean succeed, final Bundle bundle) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityCallback.receive(type, succeed, bundle);
            }
        });
    }

    void noInternetCallBack() {
        Bundle bundle = new Bundle();
        bundle.putString(RESPONSE_BODY, "No Internet Connection");
        activityCallback.receive(requestType, false, bundle);
    }

    private boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public interface Callback {
        void receive(int type, boolean success, Bundle extras);
    }

    public class MyBinder extends Binder {
        public OkhttpTaskService getService() {
            return OkhttpTaskService.this;
        }
    }
}