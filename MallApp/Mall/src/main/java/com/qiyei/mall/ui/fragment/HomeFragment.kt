package com.qiyei.mall.ui.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.qiyei.framework.extend.GlideImageLoader
import com.qiyei.framework.ui.fragment.BaseMVPFragment

import com.qiyei.mall.R
import com.qiyei.mall.extend.*
import com.qiyei.mall.injection.component.DaggerMallComponent
import com.qiyei.mall.mvp.presenter.HomeFragmentPresenter
import com.qiyei.mall.mvp.view.IHomeFragmentView
import com.qiyei.mall.ui.adapter.HomeDiscountAdapter
import com.qiyei.mall.ui.adapter.TopicAdapter
import com.qiyei.sdk.launchstarter.LauncherManager
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast
import me.crosswall.lib.coverflow.CoverFlow



/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class HomeFragment : BaseMVPFragment<HomeFragmentPresenter>(),IHomeFragmentView {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initDiscountRecyclerView()
        initTopic()
    }

    override fun onStart() {
        super.onStart()
        mHomeBanner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        mHomeBanner.startAutoPlay()
    }

    /**
     * 依赖注入
     */
    override fun initComponentInject() {
        DaggerMallComponent.builder()
                .activityComponent(mActivityComponent)
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun getTAG(): String {
        return HomeFragment::class.java.simpleName
    }

    private fun initView(){
        mHomeBanner.setImageLoader(GlideImageLoader())
        //设置图片集合
        mHomeBanner.setImages(listOf(HOME_BANNER_ONE, HOME_BANNER_TWO, HOME_BANNER_THREE, HOME_BANNER_FOUR))
        mHomeBanner.setIndicatorGravity(BannerConfig.RIGHT)
        mHomeBanner.setDelayTime(2000)
        mHomeBanner.setBannerAnimation(Transformer.Accordion)
        mHomeBanner.setOnBannerListener {
            toast("$it")
        }
        mHomeBanner.start()

        mNewsViewFlipper.setData(arrayOf("夏日炎炎，第一波福利还有30秒到达战场", "新用户立领1000元优惠券"))
    }

    private fun initDiscountRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mHomeDiscountRecyclerView.layoutManager = linearLayoutManager
        mHomeDiscountRecyclerView.adapter = HomeDiscountAdapter(context!!, mutableListOf(HOME_DISCOUNT_ONE,
                HOME_DISCOUNT_TWO, HOME_DISCOUNT_THREE, HOME_DISCOUNT_FOUR, HOME_DISCOUNT_FIVE,HOME_DISCOUNT_ONE,
                HOME_DISCOUNT_TWO, HOME_DISCOUNT_THREE, HOME_DISCOUNT_FOUR, HOME_DISCOUNT_FIVE))
        mScanImageView.viewTreeObserver.addOnPreDrawListener(object :ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                LauncherManager.getDefault().end("mScanImageView onPreDraw")
                mScanImageView.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    private fun initTopic(){

        //话题
        mTopicViewPager.adapter = TopicAdapter(context!!, listOf(HOME_TOPIC_ONE, HOME_TOPIC_TWO, HOME_TOPIC_THREE, HOME_TOPIC_FOUR, HOME_TOPIC_FIVE))
        mTopicViewPager.currentItem = 1
        mTopicViewPager.offscreenPageLimit = 5

        CoverFlow.Builder()
                .with(mTopicViewPager)
                .pagerMargin(0f)
                .scale(0.3f)
                .spaceSize(0f)
                .rotationY(0f)
                .build()
        mPagerContainer.setPageItemClickListener{ view, position ->
            toast("$position")
        }
    }
}
