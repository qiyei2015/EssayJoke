package com.qiyei.architecture.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.qiyei.architecture.R
import com.qiyei.architecture.data.bean.PageBean
import com.qiyei.architecture.data.repository.MyPageDataSource
import com.qiyei.architecture.ui.adapter.MyPageDataAdapter
import kotlinx.android.synthetic.main.activity_paging_custom_data_source.*

class PagingCustomDataSourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging_custom_data_source)

        val adapter = MyPageDataAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val data = LivePagedListBuilder<Int,PageBean>(MyDataSourceFactory(),PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .build())
                .build()

        data.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    class MyDataSourceFactory:DataSource.Factory<Int,PageBean>(){
        override fun create(): DataSource<Int, PageBean> {
            //返回创建的DataSource
            return MyPageDataSource()
        }
    }

}
