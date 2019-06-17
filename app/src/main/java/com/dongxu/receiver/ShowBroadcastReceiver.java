package com.dongxu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 类描述： （无序）广播接受者
 *
 * @author zhaishaoping
 * @data 2019-06-17 15:24
 */
public class ShowBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "dx";

    public static final String RECEIVER_ACTION = "com.dongxu.receiver.action";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "------ onReceive normal ------");

        String action = intent.getAction();
        if (RECEIVER_ACTION.equals(action)) {
            Log.i(TAG, "------ onReceive normal ------ action = " + action);
        }
    }
}
