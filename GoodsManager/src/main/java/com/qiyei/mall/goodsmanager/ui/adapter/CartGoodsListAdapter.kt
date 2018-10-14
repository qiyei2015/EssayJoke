package com.qiyei.mall.goodsmanager.ui.adapter

import android.content.Context
import android.widget.CheckBox
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.data.protocol.CartGoods
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder
import ren.qinc.numberbutton.NumberButton


/**
 * @author Created by qiyei2015 on 2018/10/14.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CartGoodsListAdapter(context: Context,list:List<CartGoods>):BaseRecyclerAdapter<CartGoods>(context,list,R.layout.layout_cart_goods_item) {

    override fun convert(holder: BaseViewHolder, item: CartGoods, position: Int) {

        holder.getView<CheckBox>(R.id.mCheckedCb).isChecked = item.isSelected
        holder.setImageUrl(R.id.mGoodsIconIv,item.goodsIcon)
        holder.setText(R.id.mGoodsDescTv,item.goodsDesc)
        holder.setText(R.id.mGoodsSkuTv,item.goodsSku)
        holder.setText(R.id.mGoodsPriceTv,item.goodsPrice.toString())
        holder.getView<NumberButton>(R.id.mGoodsCountBtn).setCurrentNumber(item.goodsCount)

    }

}