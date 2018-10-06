package com.qiyei.mall.goodsmanager.ui.adapter

import android.content.Context
import android.widget.TextView
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.goodsmanager.data.bean.CategoryItem
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class FirstCategoryAdapter(context: Context,list:List<CategoryItem>):
        BaseRecyclerAdapter<CategoryItem>(context,list, R.layout.layout_first_category_item) {

    override fun convert(holder: BaseViewHolder, item: CategoryItem?, position: Int) {
        holder.setText(R.id.mFirstCategoryNameTextView,mDatas[position].categoryName)
        holder.getView<TextView>(R.id.mFirstCategoryNameTextView).isSelected = mDatas[position].isSelected
    }

}