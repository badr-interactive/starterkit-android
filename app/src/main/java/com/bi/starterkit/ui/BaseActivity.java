package com.bi.starterkit.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.bi.starterkit.service.TaskService;
import com.bi.starterkit.utils.NotificationUtils;

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

public abstract class BaseActivity extends AppCompatActivity implements TaskService.Callback {
    private static final String TAG = BaseActivity.class.getSimpleName();
    // please refer to TaskService.java in another snippet
    private TaskService taskService;

    /**
     * It is a channel between the activity and service.
     */
    protected ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            taskService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TaskService.MyBinder binder = (TaskService.MyBinder) service;
            taskService = (TaskService) binder.getService();
            taskService.registerCallback(BaseActivity.this);
            onConnect();
        }
    };

    public void onConnect() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "resumed");
        /*
         * we do local binding here. Bind current activity to the service so the activity
         * can interact with service.
         * BIND_AUTO_CREATE means recreate the service if it is destroyed when
         * there is a bound activity.
         */
        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
        bindService(new Intent(this.getApplicationContext(), TaskService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "paused");
        super.onPause();
        unbindService(serviceConnection);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Get service which manages tasks. Often you call this method
     * from activity which extends this class.
     *
     * @return download service.
     */
    public TaskService getTaskService() {
        return taskService;
    }
}
