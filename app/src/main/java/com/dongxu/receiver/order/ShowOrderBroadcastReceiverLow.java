package com.dongxu.receiver.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 类描述： 有序广播接受者
 *
 * @author zhaishaoping
 * @data 2019-06-17 15:24
 */
public class ShowOrderBroadcastReceiverLow extends BroadcastReceiver {

    public static final String TAG = "dx";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "------ onReceive order low ------");


        String action = intent.getAction();
        if (ReceiverCommon.RECEIVER_ORDER_ACTION.equals(action)) {
            Log.i(TAG, "------ onReceive order low ------ action ------");

            //获取数据
            String data = getResultData();

            Log.i(TAG, "------ onReceive order low ------ action --- data: " + data);

            //拦截广播，并修改广播内容
            setResultData("村长说：每人发送一个3元红包！！！");
        }
    }
}
