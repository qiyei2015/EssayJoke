package com.qiyei.mall.goodsmanager.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qiyei.framework.ui.fragment.BaseMVPFragment
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.injection.component.DaggerGoodsManagerComponent
import com.qiyei.mall.goodsmanager.injection.component.GoodsManagerComponent
import com.qiyei.mall.goodsmanager.mvp.presenter.CategoryManagerPresenter
import com.qiyei.mall.goodsmanager.mvp.view.ICategoryManagerView


/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CategoryFragment : BaseMVPFragment<CategoryManagerPresenter>(),ICategoryManagerView {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.getString()
    }

    /**
     * 依赖注入
     */
    override fun initComponentInject() {
        DaggerGoodsManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun getTAG(): String {
        return CategoryFragment::class.java.simpleName
    }
}
