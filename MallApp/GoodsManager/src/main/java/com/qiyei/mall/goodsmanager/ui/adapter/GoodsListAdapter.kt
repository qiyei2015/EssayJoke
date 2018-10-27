package com.qiyei.mall.goodsmanager.ui.adapter

import android.content.Context
import com.qiyei.framework.extend.loadUrl
import com.qiyei.framework.util.YuanFenConverter
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.data.bean.Goods
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder
import kotlinx.android.synthetic.main.layout_goods_list_item.view.*

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class GoodsListAdapter(context: Context,list:List<Goods>):BaseRecyclerAdapter<Goods>(context,list,
        R.layout.layout_goods_list_item) {

    override fun convert(holder: BaseViewHolder, item: Goods, position: Int) {
        //商品图标
        holder.itemView.mGoodsIconImageView.loadUrl(item.goodsDefaultIcon)
        //商品描述
        holder.itemView.mGoodsDescTextView.text = item.goodsDesc
        //商品价格
        holder.itemView.mGoodsPriceTextView.text = YuanFenConverter.changeF2YWithUnit(item.goodsDefaultPrice)
        //商品销量及库存
        holder.itemView.mGoodsSalesStockTextView.text = "销量${item.goodsSalesCount}件     库存${item.goodsStockCount}"
    }
}