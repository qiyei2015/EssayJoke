package com.qiyei.architecture.data.repository;



import androidx.lifecycle.MutableLiveData;

import com.qiyei.architecture.data.bean.Product;
import com.qiyei.sdk.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2018/1/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ProductRepository {

    private MutableLiveData<List<Product>> mProducts;
    List<Product> list = new ArrayList<>();

    public ProductRepository(String id) {
        mProducts = new MutableLiveData<>();
    }

    /**
     * @return {@link #mProducts}
     */
    public MutableLiveData<List<Product>> getProducts(String id) {
        for (int i = 0 ;i < 10;i++){
            list.add(new Product("" + i,"name:" + i));
        }
        mProducts.setValue(list);
        return mProducts;
    }

    public void setTest(String id){
        list.add(new Product("" + id,"name:" + id));
        mProducts.setValue(list);
        ToastUtil.showLongToast("id");
    }

}
