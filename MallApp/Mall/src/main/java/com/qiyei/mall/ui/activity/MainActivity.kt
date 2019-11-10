package com.qiyei.mall.ui.activity


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat

import com.qiyei.framework.ui.activity.BaseActivity
import com.qiyei.mall.R
import com.qiyei.sdk.permission.PermissionConstant
import com.qiyei.sdk.permission.PermissionManager
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(PermissionConstant.STORAGES,2)
        }

        startActivity<HomeActivity>("key" to 20)
        finish()
    }

    override fun getTAG(): String {
        return this::class.java.simpleName
    }


}
