package com.qiyei.mall.ordermanager.ui.adapter

import android.content.Context
import android.widget.TextView
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.data.bean.ShipAddress
import com.qiyei.sdk.log.LogManager
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder
import kotlinx.android.synthetic.main.layout_ship_address_item.view.*

/**
 * @author Created by qiyei2015 on 2018/10/19.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class ShipAddressAdapter(context: Context,datas:MutableList<ShipAddress>)
    :BaseRecyclerAdapter<ShipAddress>(context,datas, R.layout.layout_ship_address_item) {

    /**
     * 监听listener
     */
    var mShipAddressListener:ShipAddressListener? = null

    override fun convert(holder: BaseViewHolder, item: ShipAddress?, position: Int) {

        LogManager.i(getTAG(),"convert,item:$item")
        holder.getView<TextView>(R.id.mSetDefaultTv).isSelected = (item?.shipIsDefault == 0)
        holder.getView<TextView>(R.id.mShipNameTv).text = item?.shipUserName + "    " + item?.shipUserMobile
        holder.getView<TextView>(R.id.mShipAddressTv).text = item?.shipAddress

        holder.getView<TextView>(R.id.mSetDefaultTv).mSetDefaultTv.setOnClickListener {
            mShipAddressListener?.let {
                it.onSetDefault(item!!)
            }
        }

        holder.getView<TextView>(R.id.mEditTv).mEditTv.setOnClickListener {
            mShipAddressListener?.let {
                it.onEdit(item!!)
            }
        }

        holder.getView<TextView>(R.id.mDeleteTv).mDeleteTv.setOnClickListener {
            mShipAddressListener?.let {
                it.onDelete(item!!)
            }
        }
    }

    /**
     * 地址操作接口
     */
    interface ShipAddressListener{

        fun onSetDefault(address:ShipAddress)

        fun onEdit(address:ShipAddress)

        fun onDelete(address:ShipAddress)
    }
}