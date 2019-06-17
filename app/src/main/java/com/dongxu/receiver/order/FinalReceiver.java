package com.dongxu.receiver.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * 类描述：有序广播 最终会传递到这里；不管该广播是否被终止。
 *
 * @author zhaishaoping
 * @data 2019-06-17 16:11
 */
public class FinalReceiver extends BroadcastReceiver {
    public static final String TAG = "dx";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "------ onReceive order final ------");

        String action = intent.getAction();
        if (ReceiverCommon.RECEIVER_ORDER_ACTION.equals(action)) {
            //获取数据
            String data = getResultData();

            Log.i(TAG, "------ onReceive order final ------ action ------ data: " + data);
        }
    }
}
