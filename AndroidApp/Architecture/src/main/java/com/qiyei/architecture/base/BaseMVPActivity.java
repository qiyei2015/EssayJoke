package com.qiyei.architecture.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

public class BaseMVPActivity<P extends BaseMVPPresenter> extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
