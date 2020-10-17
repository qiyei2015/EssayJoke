package com.qiyei.architecture.base;


import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

public class BaseMVPPresenter<T> extends MVPContract.AbsPresenter{

    protected T mView;

    public BaseMVPPresenter(@NonNull LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    public void attachView(T view){
        this.mView = (T) view;
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        super.onDestroy(owner);
        mView = null;
    }
}
