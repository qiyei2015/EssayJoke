package com.qiyei.mall.goodsmanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.goodsmanager.data.bean.CategoryItem
import com.qiyei.mall.goodsmanager.mvp.view.ICategoryManagerView
import com.qiyei.mall.goodsmanager.service.ICategoryService
import com.qiyei.sdk.log.LogManager
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CategoryManagerPresenter @Inject constructor():BasePresenter<ICategoryManagerView>() {

    @Inject
    lateinit var mCategoryService:ICategoryService

    override fun getTAG(): String {
        return CategoryManagerPresenter::class.java.simpleName
    }

    fun getCategory(id:Int){
        mCategoryService.getCategory(id).execute(object :BaseObserver<MutableList<CategoryItem>?>(mView){
            override fun onNext(t: MutableList<CategoryItem>?) {
                mView.onCategoryResult(t)
            }
        },mLifecycleProvider)
    }

}