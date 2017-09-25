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
import com.bi.starterkit.data.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

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
import okio.Buffer;

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
        Request request = new okhttp3.Request.Builder()
                .url(url)
                .method(method, method.equals(GET) ? null : requestBody)
                .headers(headers)
                .build();

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
                                result.putString(RESPONSE_DATA, responseJson.get(RESPONSE_DATA).toString());
                            }
                            sentCallback(reqType, response.isSuccessful(), result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        try {
                            Log.e(TAG, "onResponse: " + response.headers().toString());
                            Log.d("failed listener type", reqType + " " + responseString + " " + response.code());
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
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();

        return new OkHttpClient.Builder()
//                .sslSocketFactory(sslSocketFactory, trustManager)
                .connectTimeout(CONNECTION_TIMEOUT_SECOND, TimeUnit.SECONDS)
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

    private InputStream trustedCertificatesInputStream() {
        // PEM files for root certificates of Comodo and Entrust. These two CAs are sufficient to view
        // https://publicobject.com (Comodo) and https://squareup.com (Entrust). But they aren't
        // sufficient to connect to most HTTPS sites including https://godaddy.com and https://visa.com.
        // Typically developers will need to get a PEM file from their organization's TLS administrator.
        String comodoRsaCertificationAuthority = ""
                + Constants.COMODO_KEY;
        String entrustRootCertificateAuthority = ""
                + Constants.CERTIFICATE_KEY;
        return new Buffer()
                .writeUtf8(comodoRsaCertificationAuthority)
                .writeUtf8(entrustRootCertificateAuthority)
                .inputStream();
    }

    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
