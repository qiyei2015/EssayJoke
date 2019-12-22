package com.qiyei.architecture.data.repository;

import com.qiyei.architecture.data.bean.PageBean;
import com.qiyei.sdk.log.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Created by qiyei2015 on 2019/12/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MyPageRepository {
    private static final String TAG = "MyPageRepository";

    private static final int SIZE = 1000;
    /**
     * 模拟数据源数据
     */
    private static List<PageBean> data;

    static {
        data = new ArrayList<>();
        for (int i = 0 ;i < SIZE;i++){
            PageBean bean = new PageBean(i,"测试Bean数据 -> " + i, ""+new Random().nextInt(SIZE));
            data.add(bean);
        }
    }

    public MyPageRepository() {

    }

    /**
     * 加载size条数据
     * @param size
     * @return
     */
    public List<PageBean> loadData(int size){
        if (size < SIZE){
            LogManager.d(TAG,"loadData,size=" + size);
            return data.subList(0,size);
        }
        return null;
    }

    /**
     * 加载从from到to数据
     * @param from
     * @param to
     * @return
     */
    public List<PageBean> loadData(int from, int to){
        //参数检查
        return data.subList(from,to);
    }

    /**
     * 加载分页数据
     * @param page
     * @param pageSize
     * @return
     */
    public List<PageBean> loadPageData(int page, int pageSize){
        int totalPage = 0;
        if (data.size() / pageSize == 0){
            totalPage = data.size() / pageSize;
        } else {
            totalPage = data.size() / pageSize + 1;
        }

        if (page > totalPage || page < 1) {
            return null;
        }
        LogManager.d(TAG,"loadPageData,page=" + page + ",pageSize=" + pageSize);
        if (page == totalPage){
            return data.subList((page - 1) * pageSize,data.size());
        }
        return data.subList((page - 1) * pageSize,page * pageSize);
    }
}
