package com.bi.starterkit.ui.authentication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bi.starterkit.R;
import com.bi.starterkit.app.Config;
import com.bi.starterkit.controller.RealmManager;
import com.bi.starterkit.data.Constants;
import com.bi.starterkit.data.PreferenceManager;
import com.bi.starterkit.model.realm.User;
import com.bi.starterkit.model.request.auth.LoginRequest;
import com.bi.starterkit.model.request.auth.SocialLoginRequest;
import com.bi.starterkit.service.TaskService;
import com.bi.starterkit.ui.BaseActivity;
import com.bi.starterkit.ui.authentication.auth.FacebookAuth;
import com.bi.starterkit.ui.authentication.auth.GoogleAuth;
import com.bi.starterkit.ui.drawer.DrawerActivity;
import com.bi.starterkit.utils.FormHelper;
import com.bi.starterkit.utils.PermissionUtils;
import com.bi.starterkit.utils.Utils;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

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

public class BasicLoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = BasicLoginActivity.class.getSimpleName();
    private static final int MIN_PASSWORD_LENGTH = 5;

    private FirebaseAnalytics mFirebaseAnalytics;
    private ProgressDialog progressDialog;
    private String fcmToken;
    private Realm realm;

    private FacebookAuth facebookAuth;
    private GoogleAuth googleAuth;
    private Toolbar toolbar;
    private LoginButton btnFacebook;
    private SignInButton btnGoogle;
    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;
    private TextView forgotPassword, tvRegister;

    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            fcmToken = intent.getStringExtra("token");
            Log.d(TAG, "onReceive: fcm-token = " + fcmToken);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeView();

        //set permission
        setPermission();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Initialize Realm
        RealmManager.incrementCount();
        realm = RealmManager.getRealm();

        // Setup Facebook Authentication
        facebookAuth = new FacebookAuth(btnFacebook) {
            @Override
            public void onRegistrationComplete(final LoginResult loginResult) {
                SocialLoginRequest.setMode(SocialLoginRequest.AUTH_MODE.FACEBOOK);
                GraphRequest graphRequest = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                // Application code
                                try {
                                    SocialLoginRequest request = new SocialLoginRequest();
                                    request.setApp();
                                    request.setDeviceID(Utils.getDeviceUuId(BasicLoginActivity.this));
                                    request.setEmail(object.getString("email"));
                                    request.setDeviceName(Build.MODEL);
                                    request.setFcmToken(fcmToken);
                                    request.setToken(loginResult.getAccessToken().getToken());
                                    mFirebaseAnalytics.setUserProperty(getString(R.string.analytics_login_facebook), null);
                                    progressDialog.setMessage(getString(R.string.process_login));
                                    progressDialog.show();
                                    getTaskService().socialLogin(request);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, picture.type(large), gender, birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }
        };

        // Setup Google Authentication
        googleAuth = new GoogleAuth(btnGoogle, this) {
            @Override
            public void onRegistrationComplete(GoogleSignInResult result) {
                GoogleSignInAccount acct = result.getSignInAccount();
                SocialLoginRequest.setMode(SocialLoginRequest.AUTH_MODE.GOOGLE);
                SocialLoginRequest request = new SocialLoginRequest();
                request.setApp();
                request.setDeviceID(Utils.getDeviceUuId(BasicLoginActivity.this));
                request.setEmail(acct.getEmail());
                request.setDeviceName(Build.MODEL);
                request.setFcmToken(fcmToken);
                request.setToken(acct.getIdToken());
                mFirebaseAnalytics.setUserProperty(getString(R.string.analytics_login_google), null);
                progressDialog.setMessage(getString(R.string.process_login));
                progressDialog.show();
                getTaskService().socialLogin(request);
            }

            @Override
            public void onError(String s) {
                super.onError(s);
            }
        };

        fcmToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onCreate: fcm-token = " + fcmToken);
        LocalBroadcastManager.getInstance(this).registerReceiver(tokenReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));
    }


    public void initializeView() {
        progressDialog = new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnFacebook = (LoginButton) findViewById(R.id.btn_login_fb);
        btnGoogle = (SignInButton) findViewById(R.id.btn_login_google);
        etEmail = (TextInputEditText) findViewById(R.id.et_email);
        etPassword = (TextInputEditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        forgotPassword = (TextView) findViewById(R.id.tv_forgot_password);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        btnFacebook.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        RealmManager.decrementCount();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleAuth.onActivityResult(requestCode, resultCode, data);
        facebookAuth.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void receive(int type, boolean success, final Bundle extras) {
        progressDialog.dismiss();
        if (!success) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.failed)
                    .setMessage(extras.getString(TaskService.RESPONSE_MESSAGE))
                    .setNegativeButton(getString(R.string.ok), null)
                    .show();
            return;
        }
        switch (type) {
            case TaskService.REQ_LOGIN:
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, null);
                RealmManager.createOrUpdateObjectFromJson(realm, User.class, extras.getString(TaskService.RESPONSE_DATA));
                PreferenceManager.getInstance().storeBoolean(Constants.LOGGED_IN, true);
                startActivity(new Intent(this, DrawerActivity.class));
                finish();
                break;
            case TaskService.REQ_SOCIAL_LOGIN:
                RealmManager.createOrUpdateObjectFromJson(realm, User.class, extras.getString(TaskService.RESPONSE_DATA));
                PreferenceManager.getInstance().storeBoolean(Constants.LOGGED_IN, true);
                startActivity(new Intent(this, DrawerActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    private void setPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this,
                    Config.READ_PHONE_STATE_PERMISSION,
                    Manifest.permission.READ_PHONE_STATE,
                    getString(R.string.permission_rationale_phone_state),
                    false);
        }
    }

    @Override
    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE);
            finish();
        }
    }

    private boolean validate() {
        if (FormHelper.isEmptyEditor(etEmail)) {
            etEmail.setError(getString(R.string.empty_editor));
            etEmail.requestFocus();
            return false;
        }
        if (!FormHelper.isEmailValid(etEmail.getText().toString())) {
            etEmail.setError(getString(R.string.email_invalid));
            etEmail.requestFocus();
            return false;
        }
        if (FormHelper.isEmptyEditor(etPassword)) {
            etPassword.setError(getString(R.string.empty_editor));
            etPassword.requestFocus();
            return false;
        }
        if (etPassword.getText().toString().length() < MIN_PASSWORD_LENGTH) {
            etPassword.requestFocus();
            etPassword.setError(getString(R.string.minimum_password_required));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                if (validate()) {
                    LoginRequest request = new LoginRequest();
                    request.setEmail(etEmail.getText().toString());
                    request.setPassword(etPassword.getText().toString());
                    request.setDeviceID(Utils.getDeviceUuId(this));
                    request.setDeviceName(Build.MODEL);
                    progressDialog.setMessage(getString(R.string.process_login));
                    progressDialog.show();
                    getTaskService().login(request);
                }
                break;
            case R.id.tv_forgot_password:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
            default:
                break;
        }
    }
}
