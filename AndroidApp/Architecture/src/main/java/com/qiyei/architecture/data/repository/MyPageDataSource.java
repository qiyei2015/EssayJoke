package com.qiyei.architecture.data.repository;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.qiyei.architecture.data.bean.PageBean;
import com.qiyei.sdk.log.LogManager;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2019/12/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 自定义的DataSource
 */
public class MyPageDataSource extends PageKeyedDataSource<Integer, PageBean> {

    private static final String TAG = "MyPageDataSource";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, PageBean> callback) {
        LogManager.i(TAG,"loadInitial,requestedLoadSize=" + params.requestedLoadSize);

        //加载数据
        List<PageBean> data = new MyPageRepository().loadData(params.requestedLoadSize);
        //设置数据回调
        callback.onResult(data,null,2);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PageBean> callback) {
        LogManager.i(TAG,"loadBefore,key="+ params.key + ",requestedLoadSize=" + params.requestedLoadSize);
        //加载数据
        List<PageBean> data = new MyPageRepository().loadPageData(params.key,params.requestedLoadSize);
        //设置数据回调
        if (data != null){
            //加载上一页
            callback.onResult(data,params.key - 1);
        }

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PageBean> callback) {
        LogManager.i(TAG,"loadAfter,key="+ params.key + ",requestedLoadSize=" + params.requestedLoadSize);
        //加载数据
        List<PageBean> data = new MyPageRepository().loadPageData(params.key,params.requestedLoadSize);
        //设置数据回调
        if (data != null){
            //加载下一页
            callback.onResult(data,params.key + 1);
        }
    }


}
