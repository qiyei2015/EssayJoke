package com.qiyei.mall.ui.activity


import android.os.Bundle
import com.qiyei.framework.ui.activity.BaseActivity
import com.qiyei.mall.R
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity<HomeActivity>("key" to 20)
        finish()
    }

    override fun getTAG(): String {
        return this::class.java.simpleName
    }
}
