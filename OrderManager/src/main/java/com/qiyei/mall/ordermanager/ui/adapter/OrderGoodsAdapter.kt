package com.qiyei.mall.ordermanager.ui.adapter

import android.content.Context
import com.qiyei.framework.util.YuanFenConverter
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.data.bean.OrderGoods
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder

/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class OrderGoodsAdapter(context: Context,datas:MutableList<OrderGoods>)
    :BaseRecyclerAdapter<OrderGoods>(context,datas, R.layout.layout_order_goods_item) {


    override fun convert(holder: BaseViewHolder, item: OrderGoods?, position: Int) {

        holder.setImageUrl(R.id.mGoodsIconIv,item?.goodsIcon)

        holder.setText(R.id.mGoodsDescTv,item?.goodsDesc)
        holder.setText(R.id.mGoodsSkuTv,item?.goodsSku)
        holder.setText(R.id.mGoodsPriceTv, YuanFenConverter.changeF2YWithUnit(item?.goodsPrice?:0L))
        holder.setText(R.id.mGoodsCountTv,"x${item?.goodsCount}")
    }

}