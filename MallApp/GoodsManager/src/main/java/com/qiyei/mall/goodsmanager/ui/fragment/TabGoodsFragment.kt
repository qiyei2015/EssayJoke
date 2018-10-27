package com.qiyei.mall.goodsmanager.ui.fragment


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.qiyei.framework.extend.GlideImageLoader
import com.qiyei.framework.ui.activity.BaseActivity
import com.qiyei.framework.ui.fragment.BaseMVPFragment
import com.qiyei.framework.util.YuanFenConverter
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.common.GoodsConstant
import com.qiyei.mall.goodsmanager.data.bean.Goods
import com.qiyei.mall.goodsmanager.event.AddCartEvent
import com.qiyei.mall.goodsmanager.event.GoodsDetailImageEvent
import com.qiyei.mall.goodsmanager.event.SkuChangedEvent
import com.qiyei.mall.goodsmanager.event.UpdateCartCountEvent
import com.qiyei.mall.goodsmanager.injection.component.DaggerGoodsComponent
import com.qiyei.mall.goodsmanager.injection.module.GoodsModule
import com.qiyei.mall.goodsmanager.mvp.presenter.GoodsSkuPresenter
import com.qiyei.mall.goodsmanager.mvp.view.IGoodsSkuView
import com.qiyei.mall.goodsmanager.ui.activity.GoodsDetailActivity
import com.qiyei.mall.goodsmanager.view.GoodsSkuPopView
import com.qiyei.provider.service.mall.IGoodsManagerService
import com.qiyei.provider.service.mall.MallServiceConstant
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
     * sku弹出View
     */
    private lateinit var mSkuPop: GoodsSkuPopView
    //SKU弹层出场动画
    private lateinit var mAnimationStart: Animation
    //SKU弹层退场动画
    private lateinit var mAnimationEnd: Animation
    /**
     * 商品列表id
     */
    private var mGoodsId:Int = -1
    /**
     * 当前商品
     */
    private var mCurrentGoods:Goods? = null

    @Autowired(name = MallServiceConstant.GOODS_MANAGER_SERVICE_PATH)
    lateinit var mGoodsManagerService: IGoodsManagerService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_goods_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
        initSkuPop()
        initAnimation()
        initObserver()
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

    override fun onClick(view: View) {
        super.onClick(view)
        when(view.id){
            R.id.mSkuViewLayout -> {
                mSkuPop.showAtLocation((activity as GoodsDetailActivity).contentView
                        , Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL,
                        0, 0
                )
                (activity as BaseActivity).contentView.startAnimation(mAnimationStart)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    override fun onGoodsSkuResult(goods: Goods) {
        mCurrentGoods = goods
        mGoodsDetailBanner.setImages(goods.goodsBanner.split(","))
        mGoodsDetailBanner.start()
        updateView(goods)
        Bus.send(GoodsDetailImageEvent(goods.goodsDetailOne, goods.goodsDetailTwo))
        loadPopData(goods)
    }

    /**
     * 加入购物车回调
     */
    override fun onAddCartResult(num: Int) {
        //保存数量
        mGoodsManagerService.updateCartGoodsCount(num)
        Bus.send(UpdateCartCountEvent())
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
        mSkuViewLayout.setOnClickListener(this)
    }

    /**
     * 初始化sku
     */
    private fun initSkuPop() {
        mSkuPop = GoodsSkuPopView(activity!!)
        mSkuPop.setOnDismissListener{
            (activity as BaseActivity).contentView.startAnimation(mAnimationEnd)
        }
    }

    /**
     * 初始化缩放动画
     */
    private fun initAnimation() {
        mAnimationStart = ScaleAnimation(
                1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationStart.duration = 500
        mAnimationStart.fillAfter = true

        mAnimationEnd = ScaleAnimation(
                0.95f, 1f, 0.95f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationEnd.duration = 500
        mAnimationEnd.fillAfter = true
    }

    /**
     * 添加监听 使用的rxbus,不建议用，后期整改
     */
    private fun initObserver(){
        //注册sku改变事件
        Bus.observe<SkuChangedEvent>().subscribe {
            mSkuSelectedTextView.text = mSkuPop.getSelectSku() +GoodsConstant.SKU_SEPARATOR + mSkuPop.getSelectCount()+"件"
        }.registerInBus(this)

        //注册sku改变事件
        Bus.observe<AddCartEvent>().subscribe {
            addGoodsToCart()
        }.registerInBus(this)
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

    /**
     * 加载sku数据
     */
    private fun loadPopData(goods: Goods){
        mSkuPop.setGoodsIcon(goods.goodsDefaultIcon)
        mSkuPop.setGoodsCode(goods.goodsCode)
        mSkuPop.setGoodsPrice(goods.goodsDefaultPrice)
        mSkuPop.setSkuData(goods.goodsSku)
    }

    /**
     * 添加商品到购物车
     */
    private fun addGoodsToCart(){
        if (mCurrentGoods != null){
            mPresenter.addCart(mCurrentGoods!!,mSkuPop.getSelectCount(),mSkuPop.getSelectSku())
        }
    }
}
