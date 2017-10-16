package com.qiyei.sdk.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.qiyei.sdk.ioc.ViewUtils;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/6.
 * Version: 1.0
 * Description: 整合应用的BaseActivity.只能放一些通用的方法，基本每个Activity都需要使用的方法，readDataBase最好不要放进来 ，
 *  如果是两个或两个以上的地方要使用,最好写一个工具类
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected final String TAG = BaseActivity.class.getSimpleName();

    /**
     * context
     */
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        ViewUtils.inject(this);
        //initData();
        //initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ViewUtils.unInject(this);
    }

    /**
     * 设置setContentView布局
     */
    protected abstract void initContentView();

    /**
     * 初始化View
     */
    protected abstract void initView();
    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 根据id返回对应的View对象
     * @param id
     * @param <T>
     * @return
     */
    protected  <T extends View> T ViewById(int id){
        return (T) findViewById(id);
    }

    /**
     * 启动Activity
     * @param clazz
     */
    protected void startActivity(Class<?> clazz){
        startActivity(new Intent(this,clazz));
    }

}
