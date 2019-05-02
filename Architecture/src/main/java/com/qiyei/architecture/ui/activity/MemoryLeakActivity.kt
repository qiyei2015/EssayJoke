package com.qiyei.architecture.ui.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.qiyei.architecture.R
import com.qiyei.architecture.util.SingleInstance

class MemoryLeakActivity : AppCompatActivity() {


    private lateinit var btn1:Button
    private lateinit var btn2:Button
    private lateinit var btn3:Button
    private lateinit var btn4:Button

    lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_leak)
        initView()
        context = this
        btn1.setOnClickListener({it ->
            SingleInstance.getInstance(context)
            //finish()
        })

        btn2.setOnClickListener{

        }
    }

    /**
     * 私有函数
     */
    private fun initView(){
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)
    }

}
