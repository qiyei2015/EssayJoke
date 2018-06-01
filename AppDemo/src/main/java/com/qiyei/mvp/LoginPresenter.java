package com.qiyei.mvp;

/**
 * @author Created by qiyei2015 on 2018/5/1.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class LoginPresenter implements ILoginPresenter<ILoginView> {

    private ILoginView mLoginView;

    @Override
    public void login(String name, String password) {

    }

    @Override
    public void onBindView(ILoginView iLoginView) {
        mLoginView = iLoginView;
    }

    @Override
    public void onUnbindView() {
        mLoginView = null;
    }



    @Override
    public void onCreate() {

    }



    @Override
    public void onDestory() {

    }


}
