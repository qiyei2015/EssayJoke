package com.qiyei.architecture.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.qiyei.architecture.model.Product;

/**
 * @author Created by qiyei2015 on 2018/2/9.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ProductViewModel extends AndroidViewModel {

    private MutableLiveData<Product> mLiveData;

    public ProductViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Product> getLiveData(){
        mLiveData = new MutableLiveData<>();
        return mLiveData;
    }

}
