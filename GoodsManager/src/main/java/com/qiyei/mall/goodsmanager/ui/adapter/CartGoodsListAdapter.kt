package com.qiyei.mall.goodsmanager.ui.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import com.eightbitlab.rxbus.Bus
import com.qiyei.framework.util.YuanFenConverter
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.common.getEditText
import com.qiyei.mall.goodsmanager.data.protocol.CartGoods
import com.qiyei.mall.goodsmanager.event.UpdateAllChecked
import com.qiyei.mall.goodsmanager.event.UpdateTotalPriceEvent
import com.qiyei.sdk.log.LogManager
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

    companion object {
        const val TAG = "CartGoodsListAdapter"
    }

    override fun convert(holder: BaseViewHolder, item: CartGoods, position: Int) {

        holder.getView<CheckBox>(R.id.mCheckedCb).isChecked = item.isSelected
        holder.setImageUrl(R.id.mGoodsIconIv,item.goodsIcon)
        holder.setText(R.id.mGoodsDescTv,item.goodsDesc)
        holder.setText(R.id.mGoodsSkuTv,item.goodsSku)
        holder.setText(R.id.mGoodsPriceTv, YuanFenConverter.changeF2YWithUnit(item.goodsPrice))
        holder.getView<NumberButton>(R.id.mGoodsCountBtn).setCurrentNumber(item.goodsCount)

        //设置选中监听
        holder.getView<CheckBox>(R.id.mCheckedCb).setOnClickListener{
            item.isSelected = holder.getView<CheckBox>(R.id.mCheckedCb).isChecked
            //是否全部选择
            val allChecked = datas.all {
                it.isSelected
            }
            LogManager.i(TAG,"UpdateAllChecked,allChecked:$allChecked")
            Bus.send(UpdateAllChecked(allChecked))
            notifyDataSetChanged()
        }

        holder.getView<NumberButton>(R.id.mGoodsCountBtn).getEditText().addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null){
                    return
                }
                item.goodsCount = s.toString().toInt()
                LogManager.i(TAG,"UpdateTotalPriceEvent:${item.goodsCount}")
                Bus.send(UpdateTotalPriceEvent())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

}