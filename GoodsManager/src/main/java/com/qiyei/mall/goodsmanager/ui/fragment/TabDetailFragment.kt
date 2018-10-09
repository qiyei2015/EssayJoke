package com.qiyei.mall.goodsmanager.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.qiyei.framework.extend.loadUrl
import com.qiyei.framework.ui.fragment.BaseMVPFragment
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.event.GoodsDetailImageEvent
import com.qiyei.mall.goodsmanager.injection.component.DaggerGoodsComponent
import com.qiyei.mall.goodsmanager.injection.module.GoodsModule
import com.qiyei.mall.goodsmanager.mvp.presenter.GoodsDetailPresenter
import com.qiyei.mall.goodsmanager.mvp.view.IGoodsDetailView
import kotlinx.android.synthetic.main.fragment_detail_tab.*


/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class TabDetailFragment : BaseMVPFragment<GoodsDetailPresenter>(),IGoodsDetailView {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    /**
     * 依赖注入
     */
    override fun initComponentInject() {
        DaggerGoodsComponent.builder()
                .activityComponent(mActivityComponent)
                .goodsModule(GoodsModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun getTAG(): String {
        return TabDetailFragment::class.java.simpleName
    }


    private fun initView(){

    }

    private fun initObserver(){
        Bus.observe<GoodsDetailImageEvent>()
                .subscribe { t: GoodsDetailImageEvent ->
                    run {
                        mGoodsDetailOneIv.loadUrl(t.imgOne)
                        mGoodsDetailTwoIv.loadUrl(t.imgTwo)
                    }
                }
                .registerInBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}
