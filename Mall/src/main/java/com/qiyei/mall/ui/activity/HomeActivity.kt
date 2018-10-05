package com.qiyei.mall.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qiyei.framework.ui.fragment.FragmentHelper
import com.qiyei.mall.R
import com.qiyei.mall.ui.fragment.HomeFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_home.*
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity() {

    private lateinit var fragmentHelper:FragmentHelper

    private val mHomeFragment:HomeFragment by lazy { HomeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fragmentHelper = FragmentHelper(supportFragmentManager,R.id.mHomeContent)
        initView()
        initFragment()
    }


    private fun initView(){

        Observable.timer(2,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            mHomeBottomNavigationBar.setCartBadgeCount(20)
        }

        Observable.timer(5,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            mHomeBottomNavigationBar.setMessageBadgeVisibility(true)
        }

    }

    private fun initFragment(){
        fragmentHelper.add(mHomeFragment)
    }

}
