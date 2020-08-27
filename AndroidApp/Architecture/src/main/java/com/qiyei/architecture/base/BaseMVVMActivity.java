package com.qiyei.architecture.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

public class BaseMVVMActivity<VB extends ViewBinding,VM extends ViewModel> extends BaseActivity{

    protected VB mViewBinding;
    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
