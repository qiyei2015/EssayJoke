package com.qiyei.mall.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qiyei.mall.R
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity<HomeActivity>("key" to 20)
    }
}
