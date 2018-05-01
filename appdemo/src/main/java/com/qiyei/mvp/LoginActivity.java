package com.qiyei.mvp;

import com.qiyei.mvp.base.BaseActivity;

/**
 * @author Created by qiyei2015 on 2018/5/1.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class LoginActivity extends BaseActivity<ILoginView,ILoginPresenter<ILoginView>> implements ILoginView {


    @Override
    public void showLogin(String message) {

    }


    @Override
    protected ILoginView getView() {
        return this;
    }

    @Override
    protected ILoginPresenter<ILoginView> getPresenter() {
        return new LoginPresenter();
    }


}
