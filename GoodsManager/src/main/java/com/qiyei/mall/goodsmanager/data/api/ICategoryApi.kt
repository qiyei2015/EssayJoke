package com.qiyei.mall.goodsmanager.data.api

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.mall.goodsmanager.data.bean.CategoryItem
import com.qiyei.mall.goodsmanager.data.protocol.CategoryReq
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface ICategoryApi {
    /**
     * 获取商品分类列表
     */
    @POST("category/getCategory")
    fun getCategory(@Body req: CategoryReq): Observable<BaseResp<MutableList<CategoryItem>?>>
}