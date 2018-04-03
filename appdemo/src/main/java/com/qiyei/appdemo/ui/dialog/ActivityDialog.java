package com.qiyei.appdemo.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.qiyei.appdemo.R;
import com.qiyei.sdk.log.LogManager;

/**
 * @author Created by qiyei2015 on 2018/4/2.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ActivityDialog extends Activity {

    private static final String TAG = ActivityDialog.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        //点击空白处可以消失
        setFinishOnTouchOutside(false);
        LogManager.i(TAG,"onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        LogManager.i(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogManager.i(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogManager.i(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogManager.i(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        LogManager.i(TAG,"onDestroy");
        super.onDestroy();
    }


}
