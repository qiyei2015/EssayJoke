package com.qiyei.ui.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.qiyei.ui.R;

import static com.qiyei.ui.ui.activity.LifeCycleDemoActivity.SECOND_TAG;

public class LifeCycleDemo2Activity extends AppCompatActivity {
    private static final String TAG = "LifeCycleDemo2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_demo2);
        Log.i(TAG,SECOND_TAG + "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,SECOND_TAG + "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,SECOND_TAG + "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,SECOND_TAG + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,SECOND_TAG + "onStop");
    }

    @Override
    public void finish() {
        super.finish();
        Log.i(TAG,SECOND_TAG + "finish");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,SECOND_TAG + "onDestroy");
    }


}
