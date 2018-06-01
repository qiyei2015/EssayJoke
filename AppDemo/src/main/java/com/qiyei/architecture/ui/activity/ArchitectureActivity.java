package com.qiyei.architecture.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.View;

import com.qiyei.appdemo.R;
import com.qiyei.architecture.ui.fragment.ProductListFragment;
import com.qiyei.framework.titlebar.CommonTitleBar;
import com.qiyei.sdk.base.BaseActivity;
import com.qiyei.sdk.log.LogManager;

/**
 * @author Created by qiyei2015 on 2018/1/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ArchitectureActivity extends BaseActivity {

    private final static String TAG = "TTT Activity ";


    /**
     * 标题栏
     */
    private CommonTitleBar mTitleBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogManager.i(TAG,"onCreate start");
        super.onCreate(savedInstanceState);
        initData();
        if (savedInstanceState == null) {
            ProductListFragment fragment = new ProductListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, fragment, ProductListFragment.TAG).commit();
        }
        initView();

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        LogManager.i(TAG,"onCreate end");
    }


    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_architecture);
    }

    @Override
    protected void initView() {
        mTitleBar = new CommonTitleBar.Builder(this)
                .setTitle("Android 架构组件")
                .setRightText("维护中")
                .build();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    protected void onStart() {
        LogManager.i(TAG,"onStart start");
        super.onStart();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        LogManager.i(TAG,"onStart end");
    }

    @Override
    protected void onResume() {
        LogManager.i(TAG,"onResume start");
        super.onResume();
        LogManager.i(TAG,"onResume end");
    }

    @Override
    protected void onPause() {
        LogManager.i(TAG,"onPause start");
        super.onPause();
        LogManager.i(TAG,"onPause end");
    }

    @Override
    protected void onStop() {
        LogManager.i(TAG,"onStop start");
        super.onStop();
        LogManager.i(TAG,"onStop end");
    }

    @Override
    protected void onDestroy() {
        LogManager.i(TAG,"onDestroy start");
        super.onDestroy();
        LogManager.i(TAG,"onDestroy end");
    }
}
