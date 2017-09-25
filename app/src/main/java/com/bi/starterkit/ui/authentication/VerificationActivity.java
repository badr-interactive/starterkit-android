package com.bi.starterkit.ui.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bi.starterkit.R;
import com.bi.starterkit.model.request.auth.ResetPasswordRequest;
import com.bi.starterkit.service.TaskService;
import com.bi.starterkit.ui.BaseActivity;
import com.bi.starterkit.utils.FormHelper;

import java.util.Set;

/**
 * starterkit-android
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
 * Created by Roland on 9/12/2017.
 */

public class VerificationActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = VerificationActivity.class.getSimpleName();
    private static final int MIN_PASSWORD_LENGTH = 5;

    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private TextInputEditText etCode, etPassword, etConfirmPassword;
    private Button btnSubmit;

    private String code;
    private String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        onNewIntent(getIntent());

        initialize();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        String data = intent.getDataString();
        Log.d(TAG, "onNewIntent: " + data);
        Uri uri = Uri.parse(data);
        code = uri.getQueryParameter("token");
        email = uri.getQueryParameter("email");
    }

    private void initialize() {
        progressDialog = new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etCode = (TextInputEditText) findViewById(R.id.et_code);
        etPassword = (TextInputEditText) findViewById(R.id.et_password);
        etConfirmPassword = (TextInputEditText) findViewById(R.id.et_confirm_password);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (code != null && code.trim().length() != 0) {
            etCode.setText(code);
        }

        btnSubmit.setOnClickListener(this);
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

    private boolean validate() {
        if (FormHelper.isEmptyEditor(etCode)) {
            etCode.setError(getString(R.string.empty_editor));
            etCode.requestFocus();
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
            case TaskService.REQ_RESET_PASSWORD:
                startActivity(new Intent(this, BasicLoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (validate()) {
                    ResetPasswordRequest request = new ResetPasswordRequest(etCode.getText().toString(),
                            etPassword.getText().toString(),
                            etConfirmPassword.getText().toString());
                    getTaskService().resetPassword(request);
                }
                break;
        }
    }
}
