package com.qiyei.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Created by qiyei2015 on 2018/5/1.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: MVP下的公共基础Activity
 */
public abstract class BaseActivity<V extends IView,P extends IPresenter<V>> extends AppCompatActivity{

    private V mView;

    private P mPresenter;

    private IViewPresenterFactory<V,P> mFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFactory = new IViewPresenterFactory<V, P>() {
            @Override
            public V createView() {
                return getView();
            }

            @Override
            public P createPresenter() {
                return getPresenter();
            }
        };
        mView = mFactory.createView();
        mPresenter = mFactory.createPresenter();
        //绑定View
        mPresenter.onBindView(mView);
        //调用onCreate()
        mPresenter.onCreate();

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        mPresenter.onUnbindView();
        super.onDestroy();
    }

    /**
     * 创建View
     * @return
     */
    protected abstract V getView();

    /**
     * 创建Presenter
     * @return
     */
    protected abstract P getPresenter();

}
