package com.qiyei.framework.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qiyei.sdk.log.LogManager;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/23.
 * Version: 1.0
 * Description: 所有Fragment的基类
 */
public abstract class BaseFragment extends RxFragment{

    /**
     * Activity中的context
     */
    protected Context mContext;
    /**
     * 外部传递过来的参数
     */
    protected Bundle mArgs;

    /**
     * 无参构造函数
     */
    public BaseFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = getArguments();
        mContext = getContext();
    }

    /**
     * 获取调试TAG
     * @return
     */
    abstract protected String getTAG();

    /**
     * 启动Activity
     * @param clazz
     */
    protected void startActivity(Class<?> clazz){
        if (mContext != null){
            LogManager.i(getTAG(),"startActivity,clazz:" + clazz);
            startActivity(new Intent(mContext,clazz));
            return;
        }
        LogManager.i(getTAG(),"startActivity failed,mContext is null,clazz:" + clazz);
    }

}
