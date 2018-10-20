package com.qiyei.mall.ordermanager.ui.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.qiyei.framework.constant.OrderConstant
import com.qiyei.mall.ordermanager.ui.fragment.OrderListFragment


/**
 * @author Created by qiyei2015 on 2018/10/20.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class OrderListPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    val mTitles = arrayListOf<String>("全部","待付款","待收货","已完成","已取消")

    override fun getItem(position: Int): Fragment {
        var mFragment:OrderListFragment = OrderListFragment()
        val bundle = Bundle()
        bundle.putInt(OrderConstant.User.KEY_ORDER_STATUS,position)
        mFragment.arguments = bundle
        return mFragment
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}