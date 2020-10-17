package com.qiyei.architecture.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewbinding.ViewBinding;

import com.qiyei.architecture.databinding.ActivityViewBindingBinding;

public abstract class BaseMVVMActivity<VB extends ViewBinding,VM extends ViewModel> extends BaseActivity{

    protected VB mViewBinding;
    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(getViewModelClazz());
        mViewBinding = (VB) ActivityViewBindingBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

    }

    protected abstract Class<VM> getViewModelClazz();


}
