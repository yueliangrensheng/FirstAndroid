package com.dongxu.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 类描述：
 *         startService: (多次调用的话，只会走 onStartCommand )
 *                  onCreate --> onStartCommand
 *
 *         stopService:
 *                  onDestroy
 *
 * @author zhaishaoping
 * @data 2019-06-17 10:45
 */
public class ShowService extends Service {

    private static final String TAG = "dx";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "------ onBind ------");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "------ onCreate ------");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "------ onStartCommand ------");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "------ onDestroy ------");
    }
}
