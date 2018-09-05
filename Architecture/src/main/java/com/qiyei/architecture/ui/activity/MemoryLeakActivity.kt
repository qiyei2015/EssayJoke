package com.qiyei.architecture.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qiyei.architecture.R

class MemoryLeakActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_leak)
    }
}
