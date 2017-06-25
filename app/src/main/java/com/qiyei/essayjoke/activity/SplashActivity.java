package com.qiyei.essayjoke.activity;

import android.os.Bundle;
import android.view.View;

import com.qiyei.essayjoke.R;
import com.qiyei.framework.activity.BaseSkinActivity;

public class SplashActivity extends BaseSkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {

    }


}
