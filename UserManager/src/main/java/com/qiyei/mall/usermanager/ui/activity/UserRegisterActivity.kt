package com.qiyei.mall.usermanager.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qiyei.mall.usermanager.R

import org.jetbrains.anko.toast

class UserRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)
        //anko 框架
        var value = intent.getIntExtra("key",-1)
        toast(""+value)
    }
}
