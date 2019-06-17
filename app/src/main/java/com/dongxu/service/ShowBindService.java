package com.dongxu.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 类描述：
 *      bindService：（多次调用不会走任何下面的方法）
 *              onCreate --> onBind  (onStartCommand方法不会被调用)
 *
 *      unbindService:（unbindService():只可以被调用一次，否则将会抛出异常）
 *              onUnbind --> onDestroy
 *
 *
 *
 * @author zhaishaoping
 * @data 2019-06-17 10:45
 */
public class ShowBindService extends Service {

    private static final String TAG = "dx";

    // 提供给 Client 调用
    private MyBind binder = new MyBind();

    public class MyBind extends Binder {
        public MyBind() {
            Log.i(TAG, "------ myBind ------");
        }

        /**
         * 方法描述：返回 Service，供 Client 调用它的 public类型的方法
         * @author zhaishaoping
         * @time 2019-06-17 13:54
         * @return
         */
        public ShowBindService getService() {
            return ShowBindService.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "------ onCreate ------");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "------ onBind ------");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "------ onStartCommand ------");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "------ onUnbind ------");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "------ onDestroy ------");
    }


    /**
     * 方法描述：定义 Service的public 方法。
     * @author zhaishaoping
     * @time 2019-06-17 13:53
     * @return
     */
    public void method(){
        Log.i(TAG, "------ method ------");
    }
}
