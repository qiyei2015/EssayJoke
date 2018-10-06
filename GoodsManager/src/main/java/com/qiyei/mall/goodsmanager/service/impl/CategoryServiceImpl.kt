package com.qiyei.mall.goodsmanager.service.impl

import com.qiyei.framework.extend.baseRespConvert
import com.qiyei.mall.goodsmanager.data.bean.Category
import com.qiyei.mall.goodsmanager.data.respository.CategoryRepository
import com.qiyei.mall.goodsmanager.service.ICategoryService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CategoryServiceImpl @Inject constructor():ICategoryService {

    @Inject
    lateinit var mRepository: CategoryRepository

    override fun getCategory(id:Int): Observable<MutableList<Category>?>{
        return mRepository.getCategory(id).baseRespConvert()
    }
}