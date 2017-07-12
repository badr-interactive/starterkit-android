package com.bi.starterkit.ui.authentication.auth;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

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

public abstract class FacebookAuth {
    private final LoginButton loginButton;
    private final CallbackManager callbackManager;

    public FacebookAuth(final LoginButton loginBtn) {
        callbackManager = CallbackManager.Factory.create();
        this.loginButton = loginBtn;
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday"));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                onRegistrationComplete(loginResult);
            }

            @Override
            public void onCancel() {
                onAuthCancelled();
            }

            @Override
            public void onError(FacebookException exception) {
                onAuthError();
            }
        });

    }

    /**
     * Called if the authentication is cancelled by the user.
     * <p>
     * Adapter method, developer might want to override this method  to provide
     * custom logic.
     */
    public void onAuthCancelled() {
    }

    /**
     * Called if the authentication fails.
     * <p>
     * Adapter method, developer might want to override this method  to provide
     * custom logic.
     */
    public void onAuthError() {
    }

    /**
     * Notify this class about the {@link FragmentActivity#onResume()} event.
     */
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Called once we obtain a token from Facebook API.
     *
     * @param loginResult contains the token obtained from Facebook API.
     */
    public abstract void onRegistrationComplete(final LoginResult loginResult);
}
