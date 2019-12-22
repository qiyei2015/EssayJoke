package com.qiyei.architecture.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.qiyei.architecture.R
import com.qiyei.architecture.data.bean.PageBean
import com.qiyei.sdk.log.LogManager

/**
 * @author Created by qiyei2015 on 2019/12/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class MyPageDataAdapter :PagedListAdapter<PageBean,MyPageDataAdapter.MyPageBeanViewHolder>(DIFF){

    companion object{
        private val TAG = "MyPageDataAdapter"

        private val DIFF = object :DiffUtil.ItemCallback<PageBean>(){

            override fun areItemsTheSame(oldItem: PageBean, newItem: PageBean): Boolean {
                return oldItem.id == newItem.id;
            }

            override fun areContentsTheSame(oldItem: PageBean, newItem: PageBean): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageBeanViewHolder {
        LogManager.i(TAG,"onCreateViewHolder")
        return MyPageBeanViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MyPageBeanViewHolder, position: Int) {
        LogManager.i(TAG,"position:" + getItem(position).toString())
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class MyPageBeanViewHolder(parent:ViewGroup):RecyclerView.ViewHolder(LayoutInflater.from(
            parent.context).inflate(R.layout.recyclerview_item_my_page_data_source,parent,false)){
        val nameView = itemView.findViewById<TextView>(R.id.name)
        val idView = itemView.findViewById<TextView>(R.id.id)
        val descriptionView = itemView.findViewById<TextView>(R.id.description)

        fun bind(bean:PageBean){
            idView.text = "id:${bean.id}"
            nameView.text = "name:${bean.name}"
            descriptionView.text = "description:${bean.description}"
        }
    }




}