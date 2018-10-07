package com.qiyei.mall.goodsmanager.ui.activity

import android.os.Bundle
import android.view.View
import com.qiyei.framework.titlebar.TabLayoutTitleBar
import com.qiyei.framework.ui.activity.BaseActivity
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.ui.adapter.GoodsDetailViewPagerAdapter
import kotlinx.android.synthetic.main.activity_goods_detail.*
import org.jetbrains.anko.toast

class GoodsDetailActivity : BaseActivity() {

    private lateinit var mTabLayoutTitleBar: TabLayoutTitleBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)
        initView()
    }

    override fun getTAG(): String {
        return GoodsDetailActivity::class.java.simpleName
    }

    override fun onClick(view: View) {
        super.onClick(view)
        when(view.id){
            R.id.mShareTextView -> {
                toast("分享")
            }
            R.id.mCartTextView -> {
                toast("购物车")
            }
            R.id.mAddToCartButton -> {
                toast("添加到购物车")
            }
        }
    }

    private fun initView(){
        mGoodsViewPager.adapter = GoodsDetailViewPagerAdapter(supportFragmentManager,this)
        mTabLayoutTitleBar = TabLayoutTitleBar.Builder(this)
                .setupWithViewPager(mGoodsViewPager)
                .build()

        mShareTextView.setOnClickListener(this)
        mCartTextView.setOnClickListener(this)
        mAddToCartButton.setOnClickListener(this)
    }
}
