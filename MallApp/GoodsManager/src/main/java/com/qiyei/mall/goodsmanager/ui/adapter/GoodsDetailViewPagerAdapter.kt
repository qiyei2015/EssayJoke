package com.qiyei.mall.goodsmanager.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.qiyei.mall.goodsmanager.ui.fragment.TabDetailFragment
import com.qiyei.mall.goodsmanager.ui.fragment.TabGoodsFragment

/**
 * @author Created by qiyei2015 on 2018/10/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class GoodsDetailViewPagerAdapter(fragmentManager: FragmentManager, context: Context): FragmentPagerAdapter(fragmentManager) {

    private val titles = arrayOf("商品","详情")

    override fun getItem(position: Int): Fragment {
        return if (position == 0){
            TabGoodsFragment()
        }else{
            TabDetailFragment()
        }
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}