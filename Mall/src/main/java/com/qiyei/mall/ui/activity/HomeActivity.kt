package com.qiyei.mall.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qiyei.mall.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_home.*
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
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
}
