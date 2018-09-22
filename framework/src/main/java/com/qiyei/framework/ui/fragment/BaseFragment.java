package com.qiyei.framework.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/23.
 * Version: 1.0
 * Description: 所有Fragment的基类
 */
public class BaseFragment extends Fragment{

    /**
     * 调试用的TAG
     */
    private static final String TAG = Fragment.class.getSimpleName();
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

    /**
     * 创建不带Bundle参数的Fragment实例
     * @return
     */
    public static BaseFragment newInstance(){
        return new BaseFragment();
    }

    /**
     * 创建带参数Bundle的fragment
     * @param args
     * @return
     */
    public static BaseFragment newIntance(Bundle args){
        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);
        return fragment;
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
    protected String getTAG(){
        return TAG;
    }

    /**
     * 启动Activity
     * @param clazz
     */
    protected void startActivity(Class<?> clazz){
        if (mContext != null){
            startActivity(new Intent(mContext,clazz));
        }
    }

}
