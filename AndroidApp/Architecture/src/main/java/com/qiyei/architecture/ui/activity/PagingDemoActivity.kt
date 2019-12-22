package com.qiyei.architecture.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qiyei.architecture.R
import com.qiyei.framework.extend.onClick
import kotlinx.android.synthetic.main.activity_paging_demo.*
import org.jetbrains.anko.startActivity

class PagingDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging_demo)

        button1.onClick {
            startActivity<PagingCustomDataSourceActivity>()
        }

        button2.onClick {
            startActivity<PagingRoomDataSourceActivity>()
        }
    }
}
