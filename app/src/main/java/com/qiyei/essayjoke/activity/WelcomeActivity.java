package com.qiyei.essayjoke.activity;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.qiyei.essayjoke.R;
import com.qiyei.framework.activity.BaseSkinActivity;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/24.
 * Version: 1.0
 * Description: app启动的欢迎界面
 */
public class WelcomeActivity extends BaseSkinActivity {
    /**
     * 用作启动延迟
     */
    private Handler mHandler;
    /**
     * 超时时间
     */
    private static final int TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initData() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //跳转到主页
                startActivity(HomeActivity.class);
                finish();
            }
        },TIME_OUT);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        mHandler = null;
        super.onDestroy();
    }

}
