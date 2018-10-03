package com.qiyei.mall

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qiyei.mall.usermanager.ui.activity.UserLoginActivity
import com.qiyei.mall.usermanager.ui.activity.UserRegisterActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity<UserLoginActivity>("key" to 20)
    }
}
