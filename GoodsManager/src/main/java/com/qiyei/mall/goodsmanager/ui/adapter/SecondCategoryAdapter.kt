package com.qiyei.mall.goodsmanager.ui.adapter

import android.content.Context
import android.widget.ImageView
import com.qiyei.framework.extend.loadUrl
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
class SecondCategoryAdapter(context: Context, list:List<CategoryItem>):
        BaseRecyclerAdapter<CategoryItem>(context,list, R.layout.layout_second_category_item) {

    override fun convert(holder: BaseViewHolder, item: CategoryItem, position: Int) {
        holder.setText(R.id.mSecondCategoryNameTextView,item.categoryName)
        holder.getView<ImageView>(R.id.mCategoryIconImageView).loadUrl(item.categoryIcon)
    }

}