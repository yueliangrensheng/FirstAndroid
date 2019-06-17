package com.dongxu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dongxu.activity.ShowActivity;
import com.dongxu.service.ShowBindService;
import com.dongxu.service.ShowService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "dx";

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

        //3.
    }

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
