package com.qiyei.architecture.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.qiyei.appdemo.R;
import com.qiyei.architecture.ui.fragment.ProductListFragment;
import com.qiyei.framework.titlebar.CommonTitleBar;
import com.qiyei.sdk.base.BaseActivity;

/**
 * @author Created by qiyei2015 on 2018/1/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ArchitectureActivity extends BaseActivity {

    /**
     * 标题栏
     */
    private CommonTitleBar mTitleBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        if (savedInstanceState == null) {
            ProductListFragment fragment = new ProductListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, fragment, ProductListFragment.TAG).commit();
        }
        initView();
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
    public void onClick(View v) {

    }

}
