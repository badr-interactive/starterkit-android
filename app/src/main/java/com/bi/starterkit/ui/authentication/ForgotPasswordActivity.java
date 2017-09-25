package com.bi.starterkit.ui.authentication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bi.starterkit.R;
import com.bi.starterkit.model.request.auth.ForgotPasswordRequest;
import com.bi.starterkit.service.TaskService;
import com.bi.starterkit.ui.BaseActivity;
import com.bi.starterkit.utils.FormHelper;

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
 * Created by Roland on 7/12/2017.
 */

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private TextView tvHaveCode;
    private TextInputEditText etEmail;
    private Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initializeView();

    }

    private void initializeView() {
        progressDialog = new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvHaveCode = (TextView) findViewById(R.id.tv_have_code);
        etEmail = (TextInputEditText) findViewById(R.id.et_email);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvHaveCode.setOnClickListener(this);
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
            case TaskService.REQ_FORGOT_PASSWORD:
                new AlertDialog.Builder(this)
                        .setMessage(extras.getString(TaskService.RESPONSE_MESSAGE))
                        .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).show();
                break;
            default:
                break;
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
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_have_code:
                startActivity(new Intent(this, VerificationActivity.class));
                break;
            case R.id.btn_submit:
                if (validate()) {
                    ForgotPasswordRequest request = new ForgotPasswordRequest();
                    request.setEmail(etEmail.getText().toString());
                    progressDialog.setMessage(getString(R.string.process_forgot_password));
                    progressDialog.show();
                    getTaskService().forgotPassword(request);
                }
                break;
        }
    }
}
