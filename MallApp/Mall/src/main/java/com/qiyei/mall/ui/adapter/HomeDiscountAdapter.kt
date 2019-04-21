package com.qiyei.mall.ui.adapter

import android.content.Context
import android.view.ViewTreeObserver
import com.qiyei.mall.R
import com.qiyei.sdk.image.ImageManager
import com.qiyei.sdk.launchstarter.LauncherManager
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_home_discount_item.view.*

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class HomeDiscountAdapter(context: Context,datas:List<String>):BaseRecyclerAdapter<String>(context,
        datas,R.layout.layout_home_discount_item) {

    var hasStart = false

    override fun convert(holder: BaseViewHolder, item: String?, position: Int) {
        ImageManager.getInstance().loadImage(holder.getView(R.id.mGoodsIconImageView),mDatas[position])
        //静态假数据
        holder.itemView.mDiscountAfterTextView.text = "￥123.00"
        holder.itemView.mDiscountBeforeTextView.text = "$1000.00"
        if (position == 0 && !hasStart){
            hasStart = true
            holder.itemView.mDiscountAfterTextView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    LauncherManager.getDefault().end("holder.itemView onPreDraw")
                    holder.itemView.mDiscountAfterTextView.viewTreeObserver.removeOnPreDrawListener (this)
                    return true
                }
            })
            //holder.itemView.mDiscountAfterTextView.
        }
    }
}