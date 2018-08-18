package com.qiyei.android.home.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qiyei.android.R;

/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        startActivity(new Intent(this,HomeActivity.class));
    }
}
