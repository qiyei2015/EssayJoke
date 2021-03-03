package com.qiyei.ndk.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.qiyei.ndk.R
import com.qiyei.ndk.api.Demo

class JNIDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni_demo)

        Log.i("JNIDemoActivity","jni start")
        Demo().stringFromJNI()
    }
}