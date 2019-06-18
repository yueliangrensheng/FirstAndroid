package com.dongxu;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dongxu.activity.ShowActivity;
import com.dongxu.receiver.ShowBroadcastReceiver;
import com.dongxu.receiver.order.FinalReceiver;
import com.dongxu.receiver.order.ReceiverCommon;
import com.dongxu.service.ShowBindService;
import com.dongxu.service.ShowService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "dx";

    final Uri uri = Uri.parse("content://com.dx.provider.authority/user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.Activity life
        findViewById(R.id.activity_life).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowActivity.class));
            }
        });

        //2.Service
        findViewById(R.id.service_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goService();
            }
        });
        findViewById(R.id.service_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killService();
            }
        });

        findViewById(R.id.service_bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBindService();
            }
        });
        findViewById(R.id.service_unbind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unBindService();
            }
        });

        //3.BroadcastReceiver
        findViewById(R.id.receiver_no_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送无序广播
                Intent intent = new Intent(MainActivity.this, ShowBroadcastReceiver.class);
                intent.setAction(ShowBroadcastReceiver.RECEIVER_ACTION);
                sendBroadcast(intent);
            }
        });
        findViewById(R.id.receiver_has_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送有序广播
                Intent intent = new Intent();
                intent.setAction(ReceiverCommon.RECEIVER_ORDER_ACTION);

                String receiverPermission = ReceiverCommon.RECEIVER_ORDER_PERMISSION;
                BroadcastReceiver resultReceiver = new FinalReceiver();
                Handler scheduler = new Handler();
                int initialCode = 0;
                String initialData = "市长说：每人发送一个10元红包！！！";
                Bundle initialExtras = new Bundle();

                sendOrderedBroadcast(
                        intent,
                        receiverPermission,//接受这条广播所需的权限
                        resultReceiver,//最终的广播接受者，广播一定会传递给 resultReceiver;不管 广播是否被 终止； //这个Receiver在清单文件中可以不用声明 action
                        scheduler,//处理广播的分发
                        initialCode,
                        initialData,//初始数据
                        initialExtras//额外数据，如果觉得初始数据不够用，可以通过该字段添加
                );


//                sendOrderedBroadcast(intent,receiverPermission);


            }

        });


        final int[] uid = {11};
        //4. ContentProvider
        findViewById(R.id.provider_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add data

                ContentValues values = new ContentValues();
                values.put("uid", String.valueOf(uid[0]++));
                values.put("name", "li");
                values.put("age", "30");
                values.put("score", "55.7");

                //通过ContentResolver 根据URi 向 ContentProvider中插入数据
                ContentResolver contentResolver = getContentResolver();
                contentResolver.insert(uri, values);
            }
        });
        findViewById(R.id.provider_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //del data

                ContentResolver contentResolver = getContentResolver();
                int delete = contentResolver.delete(uri, "score<?", new String[]{"60"});
//                int delete = contentResolver.delete(uri, "score<60", null); //也可以使用
                if (delete > 0) {
                    Log.i(TAG, "------ del count = " + delete);
                }
            }
        });
        findViewById(R.id.provider_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update data

                ContentValues values = new ContentValues();
                values.put("score", "100");

                ContentResolver contentResolver = getContentResolver();
                int update = contentResolver.update(uri, values, "uid=?", new String[]{"11"});
                if (update > 0) {
                    Log.i(TAG, "------ update count = " + update);
                }
            }
        });
        findViewById(R.id.provider_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //query data
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(uri, new String[]{"uid", "name", "age", "score"}, null, null, null);
                if (cursor == null) {
                    return;
                }

                while (cursor.moveToNext()) {
                    int uid = cursor.getInt(cursor.getColumnIndex("uid"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int age = cursor.getInt(cursor.getColumnIndex("age"));
                    double score = cursor.getDouble(cursor.getColumnIndex("score"));

                    Log.i(TAG, "uid = " + uid + ", name = " + name + ", age = " + age + ", score = " + score);
                }

                cursor.close();


            }
        });

        findViewById(R.id.provider_observer_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().registerContentObserver(uri, true, contentObserver);
            }
        });
        findViewById(R.id.provider_observer_unregister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().unregisterContentObserver(contentObserver);
            }
        });


        //end

    }


    ContentObserver contentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);

            Log.i(TAG, "------ content observer ------");
        }
    };


    //*************************************** start Service ****************************************
    //启动Service
    Intent service;

    private void goService() {
        service = new Intent(this, ShowService.class);
        startService(service);
    }

    //kill Service
    private void killService() {
        stopService(service);
    }
    //*************************************** start Service ****************************************

    //*************************************** bind Service ****************************************

    Intent bindService;

    ShowBindService.MyBind mMyBind;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "------ onServiceConnected ------");

            if (service instanceof ShowBindService.MyBind) {
                Log.i(TAG, "------ onServiceConnected ------ ShowBindService.MyBind ------");
                mMyBind = (ShowBindService.MyBind) service;
            }

            if (mMyBind != null) {
                //通过IBinder接口 与 Service 通信
                ShowBindService sBindService = mMyBind.getService();
                //调用 Service 的 公共方法
                sBindService.method();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "------ onServiceDisconnected ------");
        }
    };

    private void unBindService() {
        // 该方法 只可被调用一次， 否则：
        //java.lang.IllegalArgumentException: Service not registered: com.dongxu.MainActivity$6@691f3bf

        try {
            unbindService(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void goBindService() {
        bindService = new Intent(this, ShowBindService.class);
        bindService(bindService, conn, Context.BIND_AUTO_CREATE);
    }

    //*************************************** bind Service ****************************************

}
