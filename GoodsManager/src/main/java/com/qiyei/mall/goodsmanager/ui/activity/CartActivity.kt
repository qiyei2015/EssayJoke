package com.qiyei.mall.goodsmanager.ui.activity


import android.os.Bundle
import com.qiyei.framework.ui.activity.BaseActivity
import com.qiyei.framework.ui.fragment.FragmentHelper
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.ordermanager.ui.fragment.CartFragment
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : BaseActivity() {

    private lateinit var fragmentHelper: FragmentHelper

    private val mCartFragment:CartFragment by lazy { CartFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        initView()
    }

    private fun initView(){
        fragmentHelper = FragmentHelper(supportFragmentManager,R.id.mContent)
        fragmentHelper.switchFragment(mCartFragment)
    }
}
