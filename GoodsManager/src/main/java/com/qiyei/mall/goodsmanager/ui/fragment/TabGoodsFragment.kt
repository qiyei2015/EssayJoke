package com.qiyei.mall.goodsmanager.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qiyei.framework.extend.GlideImageLoader
import com.qiyei.framework.ui.fragment.BaseMVPFragment
import com.qiyei.framework.util.YuanFenConverter
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.common.GoodsConstant
import com.qiyei.mall.goodsmanager.data.bean.Goods
import com.qiyei.mall.goodsmanager.injection.component.DaggerGoodsComponent
import com.qiyei.mall.goodsmanager.injection.module.GoodsModule
import com.qiyei.mall.goodsmanager.mvp.presenter.GoodsSkuPresenter
import com.qiyei.mall.goodsmanager.mvp.view.IGoodsSkuView
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_goods_tab.*
import org.jetbrains.anko.support.v4.toast


/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class TabGoodsFragment : BaseMVPFragment<GoodsSkuPresenter>(),IGoodsSkuView {

    /**
     * 商品列表id
     */
    private var mGoodsId:Int = -1
    /**
     * 当前商品
     */
    private var mCurrentGoods:Goods? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_goods_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }

    override fun onStart() {
        super.onStart()
        mGoodsDetailBanner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        mGoodsDetailBanner.startAutoPlay()
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
        return TabGoodsFragment::class.java.simpleName
    }

    override fun onGoodsSkuResult(goods: Goods) {
        mCurrentGoods = goods
        mGoodsDetailBanner.setImages(goods.goodsBanner.split(","))
        mGoodsDetailBanner.start()
        updateView(goods)
    }

    private fun initView(){
        mGoodsDetailBanner.setImageLoader(GlideImageLoader())
        //设置图片集合
        mGoodsDetailBanner.setIndicatorGravity(BannerConfig.RIGHT)
        mGoodsDetailBanner.setDelayTime(2000)
        mGoodsDetailBanner.setBannerAnimation(Transformer.Accordion)
        mGoodsDetailBanner.setOnBannerListener {
            toast("$it")
        }
        //mGoodsDetailBanner.start()
    }

    private fun loadData(){
        mGoodsId = activity?.intent?.getIntExtra(GoodsConstant.KEY_GOODS_ID,-1)?:-1
        mPresenter.getGoodsSku(mGoodsId)
    }

    /**
     * 更新View
     */
    private fun updateView(goods: Goods){
        mGoodsDescTextView.text = goods.goodsDesc
        mGoodsPriceTextView.text = YuanFenConverter.changeF2YWithUnit(goods.goodsDefaultPrice)
        mSkuSelectedTextView.text = goods.goodsDefaultSku

    }
}
