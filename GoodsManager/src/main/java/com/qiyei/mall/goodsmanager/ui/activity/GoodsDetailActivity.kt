package com.qiyei.mall.goodsmanager.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.qiyei.framework.titlebar.TabLayoutTitleBar
import com.qiyei.framework.ui.activity.BaseActivity
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.event.AddCartEvent
import com.qiyei.mall.goodsmanager.event.UpdateCartCountEvent
import com.qiyei.mall.goodsmanager.ui.adapter.GoodsDetailViewPagerAdapter
import com.qiyei.provider.common.afterLogin
import com.qiyei.provider.service.mall.IGoodsManagerService
import com.qiyei.provider.service.mall.MallServiceConstant
import com.qiyei.sdk.log.LogManager
import kotlinx.android.synthetic.main.activity_goods_detail.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import q.rorbin.badgeview.QBadgeView

class GoodsDetailActivity : BaseActivity() {

    private lateinit var mTabLayoutTitleBar: TabLayoutTitleBar
    /**
     * 购物车角标
     */
    private lateinit var mCartBadge:QBadgeView

    @Autowired(name = MallServiceConstant.GOODS_MANAGER_PATH)
    lateinit var mGoodsManagerService: IGoodsManagerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)
        initView()
        initObserver()
        loadCartCount()
    }

    override fun getTAG(): String {
        return GoodsDetailActivity::class.java.simpleName
    }

    override fun onClick(view: View) {
        super.onClick(view)
        LogManager.i(getTAG(),"id:" + view.id)
        LogManager.i(getTAG(),"R.id.mCartTextView:" + R.id.mCartTextView)
        when(view.id){
            R.id.mShareTextView -> {
                toast("分享")
            }
            //使用QBadgeView有bug
            R.id.mCartTextView -> {
                afterLogin {
                    startActivity<CartActivity>()
                }
            }
            R.id.mAddToCartButton -> {
                afterLogin {
                    Bus.send(AddCartEvent())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private fun initView(){
        mGoodsViewPager.adapter = GoodsDetailViewPagerAdapter(supportFragmentManager,this)
        mTabLayoutTitleBar = TabLayoutTitleBar.Builder(this)
                .setupWithViewPager(mGoodsViewPager)
                .build()

        mShareTextView.setOnClickListener(this)
        mCartTextView.setOnClickListener {
            afterLogin {
                startActivity<CartActivity>()
            }
        }
        mAddToCartButton.setOnClickListener(this)
        mCartBadge = QBadgeView(this)

    }

    private fun initObserver(){
        Bus.observe<UpdateCartCountEvent>()
                .subscribe {
                    updateCartBadge()
                }.registerInBus(this)
    }

    private fun loadCartCount(){
        updateCartBadge()
    }

    /**
     * 更新购物车
     */
    private fun updateCartBadge() {
        mCartBadge.badgeGravity = Gravity.END or Gravity.TOP
        mCartBadge.setGravityOffset(22f,-2f,true)
        mCartBadge.setBadgeTextSize(6f,true)
        mCartBadge.bindTarget(mCartTextView).badgeNumber = mGoodsManagerService.getCartGoodsCount()
        toast(mCartBadge.badgeNumber.toString())
    }

}
