package com.qiyei.ui.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qiyei.ui.R;
import com.qiyei.ui.ui.activity.task.Demo1Activity;

public class LifeCycleDemoActivity extends AppCompatActivity {

    private static final String TAG = "LifeCycleDemoActivity";

    public static final String SECOND_TAG = "LifeCycleDemo ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_demo);
        Log.i(TAG,SECOND_TAG + "onCreate");

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LifeCycleDemo2Activity.class));
            }
        });
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,SECOND_TAG + "onSaveInstanceState");
    }
}
