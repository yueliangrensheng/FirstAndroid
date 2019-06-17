package com.dongxu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dongxu.R;

/**
 * 类描述：
 *
 * @author zhaishaoping
 * @data 2019-06-17 09:26
 */
public class ShowActivity extends Activity {

    private static final String TAG = "dx";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_act);

        Log.i(TAG, "------ onCreate ------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "------ onStart ------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "------ onRestart ------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "------ onResume ------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "------ onPause ------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "------ onStop ------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "------ onDestroy ------");
    }


}
