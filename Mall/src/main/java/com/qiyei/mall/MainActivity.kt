package com.qiyei.mall

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qiyei.mall.usermanager.ui.activity.UserRegisterActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity<UserRegisterActivity>("key" to 20)
    }
}
