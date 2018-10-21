package com.qiyei.mall.ordermanager.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.qiyei.framework.constant.OrderConstant
import com.qiyei.framework.constant.OrderStatus
import com.qiyei.framework.extend.loadUrl
import com.qiyei.framework.util.YuanFenConverter
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.data.bean.Order
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder
import kotlinx.android.synthetic.main.layout_order_list_item.view.*
import org.jetbrains.anko.dip

/**
 * @author Created by qiyei2015 on 2018/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class OrderListAdapter(context: Context,list: MutableList<Order>)
    :BaseRecyclerAdapter<Order>(context,list, R.layout.layout_order_list_item) {

    private var mTotalCount:Int = 0
    /**
     * 点击事件listener
     */
    var mListener:OnClickListener? = null

    override fun convert(holder: BaseViewHolder?, item: Order?, position: Int) {
        //判空
        if (holder == null || item == null){
            return
        }

        if (item.orderGoodsList.size == 1){     //单个商品
            //单个商品显示
            holder.getView<RelativeLayout>(R.id.mSingleGoodsRelativeLayout).visibility = View.VISIBLE
            //隐藏多个商品视图
            holder.getView<LinearLayout>(R.id.mMultiGoodsLinearLayout).visibility = View.GONE

            val orderGoods = item.orderGoodsList[0]
            //商品图标
            holder.getView<ImageView>(R.id.mGoodsIconImageView).loadUrl(orderGoods.goodsIcon)
            //商品描述
            holder.getView<TextView>(R.id.mGoodsDescTextView).text = orderGoods.goodsDesc
            //商品价格
            holder.getView<TextView>(R.id.mGoodsPriceTextView).text = YuanFenConverter.changeF2YWithUnit(orderGoods.goodsPrice)
            //商品数量
            holder.getView<TextView>(R.id.mGoodsCountTextView).text = "x${orderGoods.goodsCount}"
            //总数量
            mTotalCount = orderGoods.goodsCount

        }else{      //多个商品视图展示

            //隐藏单个商品显示
            holder.getView<RelativeLayout>(R.id.mSingleGoodsRelativeLayout).visibility = View.GONE
            //多个商品视图显示
            holder.getView<LinearLayout>(R.id.mMultiGoodsLinearLayout).visibility = View.VISIBLE
            holder.getView<LinearLayout>(R.id.mMultiGoodsLinearLayout).removeAllViews()
            for (orderGoods in item.orderGoodsList){//动态添加商品视图
                val imageView = ImageView(mContext)
                val lp = ViewGroup.MarginLayoutParams(mContext.dip(60.0f),mContext.dip(60.0f))
                lp.rightMargin = mContext.dip(15f)
                imageView.layoutParams = lp
                imageView.loadUrl(orderGoods.goodsIcon)
                holder.getView<LinearLayout>(R.id.mMultiGoodsLinearLayout).addView(imageView)
                mTotalCount += orderGoods.goodsCount
            }
        }

        holder.getView<TextView>(R.id.mOrderInfoTextView).text = "合计${mTotalCount}件商品，总价${YuanFenConverter.changeF2YWithUnit(item.totalPrice)}"

        //根据订单状态设置颜色、文案及对应操作按钮
        when(item.orderStatus){
            OrderStatus.ORDER_WAIT_PAY -> {
                holder.getView<TextView>(R.id.mOrderStatusNameTextView).text = mContext.getString(R.string.pending_pay)
                holder.getView<TextView>(R.id.mOrderStatusNameTextView).setTextColor(mContext.resources.getColor(R.color.common_red))
                setOperateVisible(false,true,true,holder)
            }
            OrderStatus.ORDER_WAIT_CONFIRM -> {
                holder.getView<TextView>(R.id.mOrderStatusNameTextView).text = mContext.getString(R.string.pending_receive)
                holder.getView<TextView>(R.id.mOrderStatusNameTextView).setTextColor(mContext.resources.getColor(R.color.common_blue))
                setOperateVisible(true,false,true,holder)
            }

            OrderStatus.ORDER_COMPLETED -> {
                holder.getView<TextView>(R.id.mOrderStatusNameTextView).text = mContext.getString(R.string.already_complete)
                holder.getView<TextView>(R.id.mOrderStatusNameTextView).setTextColor(mContext.resources.getColor(R.color.common_yellow))
                setOperateVisible(false,false,false,holder)
            }

            OrderStatus.ORDER_CANCELED -> {
                holder.getView<TextView>(R.id.mOrderStatusNameTextView).text = mContext.getString(R.string.already_cancel)
                holder.getView<TextView>(R.id.mOrderStatusNameTextView).setTextColor(mContext.resources.getColor(R.color.common_gray))
                setOperateVisible(false,false,false,holder)
            }
        }

        //设置确认收货点击事件
        holder.itemView.mConfirmButton.setOnClickListener {
            mListener?.let {
                it.onClick(OrderConstant.OPT_ORDER_CONFIRM,item)
            }
        }

        //设置支付订单点击事件
        holder.itemView.mPayButton.setOnClickListener {
            mListener?.let {
                it.onClick(OrderConstant.OPT_ORDER_PAY,item)
            }
        }

        //设置取消订单点击事件
        holder.itemView.mCancelButton.setOnClickListener {
            mListener?.let {
                it.onClick(OrderConstant.OPT_ORDER_CANCEL,item)
            }
        }

    }

    /**
     * 设置操作按钮显示或隐藏
     */
    private fun setOperateVisible(confirmVisible: Boolean, waitPayVisible: Boolean, cancelVisible: Boolean,holder: BaseViewHolder) {
        holder.getView<Button>(R.id.mConfirmButton).visibility = if (confirmVisible) View.VISIBLE else View.GONE
        holder.getView<Button>(R.id.mPayButton).visibility = if (waitPayVisible) View.VISIBLE else View.GONE
        holder.getView<Button>(R.id.mCancelButton).visibility = if (cancelVisible) View.VISIBLE else View.GONE

        if (confirmVisible or waitPayVisible or cancelVisible){
            holder.getView<LinearLayout>(R.id.mBottomButtonLayout).visibility = View.VISIBLE
        }else{
            holder.getView<LinearLayout>(R.id.mBottomButtonLayout).visibility = View.GONE
        }
    }

    /**
     * 监听器
     */
    interface OnClickListener {

        fun onClick(type:Int,order:Order)
    }
}