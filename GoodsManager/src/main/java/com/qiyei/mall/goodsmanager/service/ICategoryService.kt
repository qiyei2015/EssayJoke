package com.qiyei.mall.goodsmanager.service

import com.qiyei.mall.goodsmanager.data.bean.CategoryItem
import io.reactivex.Observable

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface ICategoryService {

    fun getCategory(id:Int):Observable<MutableList<CategoryItem>?>
}