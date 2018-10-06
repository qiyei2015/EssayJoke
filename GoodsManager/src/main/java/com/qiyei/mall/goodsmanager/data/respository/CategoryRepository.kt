package com.qiyei.mall.goodsmanager.data.respository

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.RetrofitFactory
import com.qiyei.mall.goodsmanager.data.api.ICategoryApi
import com.qiyei.mall.goodsmanager.data.bean.CategoryItem
import com.qiyei.mall.goodsmanager.data.protocol.CategoryReq
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CategoryRepository @Inject constructor(){

    /**
     * 获取商品分类
     */
    fun getCategory(parentId: Int): Observable<BaseResp<MutableList<CategoryItem>?>> {
        return RetrofitFactory.INSTANCE.create(ICategoryApi::class.java).getCategory(CategoryReq(parentId))
    }

}