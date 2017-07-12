package com.bi.starterkit.ui.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.bi.starterkit.R;
import com.bi.starterkit.model.request.auth.RegisterRequest;
import com.bi.starterkit.service.TaskService;
import com.bi.starterkit.ui.BaseActivity;
import com.bi.starterkit.ui.main.MainActivity;
import com.bi.starterkit.utils.FormHelper;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.analytics.FirebaseAnalytics;

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

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final int MIN_PASSWORD_LENGTH = 5;

    private FirebaseAnalytics mFirebaseAnalytics;

    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private LoginButton btnFacebook;
    private SignInButton btnGoogle;
    private TextInputEditText etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeView();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    public void initializeView() {
        progressDialog = new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnFacebook = (LoginButton) findViewById(R.id.btn_login_fb);
        btnGoogle = (SignInButton) findViewById(R.id.btn_login_google);
        etEmail = (TextInputEditText) findViewById(R.id.et_email);
        etPassword = (TextInputEditText) findViewById(R.id.et_password);
        etConfirmPassword = (TextInputEditText) findViewById(R.id.et_confirm_password);
        btnRegister = (Button) findViewById(R.id.btn_register);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnFacebook.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

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
        super.onDestroy();
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {
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
            case TaskService.REQ_REGISTER:
                mFirebaseAnalytics.logEvent(getString(R.string.analytics_register), null);
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_fb:
                break;
            case R.id.btn_login_google:
                break;
            case R.id.btn_register:
                if (validate()) {
                    RegisterRequest request = new RegisterRequest();
                    request.setEmail(etEmail.getText().toString());
                    request.setPassword(etPassword.getText().toString());
                    request.setConfirmPassword(etConfirmPassword.getText().toString());
                    progressDialog.setMessage(getString(R.string.process_register));
                    progressDialog.show();
                    getTaskService().register(request);
                }
                break;
        }
    }

    private boolean validate() {
        if (!FormHelper.isEmailValid(etEmail.getText().toString())) {
            etEmail.setError(getString(R.string.email_invalid));
            etEmail.requestFocus();
            return false;
        }
        if (FormHelper.isEmptyEditor(etEmail)) {
            etEmail.setError(getString(R.string.empty_editor));
            etEmail.requestFocus();
            return false;
        }
        if (FormHelper.isEmptyEditor(etPassword)) {
            etPassword.setError(getString(R.string.empty_editor));
            etPassword.requestFocus();
            return false;
        }
        if (etPassword.getText().toString().length() < MIN_PASSWORD_LENGTH) {
            etPassword.setError(getString(R.string.minimum_password_required));
            etPassword.requestFocus();
            return false;
        }
        if (!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())) {
            etConfirmPassword.setError(getString(R.string.missmatch_password));
            etConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }
}
