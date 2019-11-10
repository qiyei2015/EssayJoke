package com.qiyei.architecture.ui.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.qiyei.architecture.data.bean.Product;


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
