package com.qiyei.architecture.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class BaseMVPActivity<P extends BaseMVPPresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (P) new BaseMVPPresenter(this);
    }

    protected abstract void attachView();
}
