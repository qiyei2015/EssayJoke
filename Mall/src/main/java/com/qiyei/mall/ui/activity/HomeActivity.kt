package com.qiyei.mall.ui.activity


import android.os.Bundle
import android.support.v4.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.qiyei.framework.ui.activity.BaseActivity
import com.qiyei.framework.ui.fragment.FragmentHelper
import com.qiyei.mall.R
import com.qiyei.mall.goodsmanager.ui.fragment.CategoryFragment
import com.qiyei.mall.messagemanager.ui.fragment.MessageFragment
import com.qiyei.mall.ordermanager.ui.fragment.CartFragment
import com.qiyei.mall.ui.fragment.*
import com.qiyei.mall.usermanager.ui.fragment.UserFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_home.*
import java.util.concurrent.TimeUnit

class HomeActivity : BaseActivity() {

    private lateinit var fragmentHelper:FragmentHelper

    private val mHomeFragment:HomeFragment by lazy { HomeFragment() }
    private val mCategoryFragment: CategoryFragment by lazy { CategoryFragment() }
    private val mCartFragment: CartFragment by lazy { CartFragment() }
    private val mMessageFragment: MessageFragment by lazy { MessageFragment() }
    private val mUserFragment: UserFragment by lazy { UserFragment() }
    /**
     * fragment列表
     */
    private lateinit var mFragmentList:List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fragmentHelper = FragmentHelper(supportFragmentManager,R.id.mHomeContent)
        initView()
        initFragment()
    }

    override fun getTAG(): String {
        return this::class.java.simpleName
    }

    private fun initView(){

        Observable.timer(2,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            mHomeBottomNavigationBar.setCartBadgeCount(20)
        }

        Observable.timer(5,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            mHomeBottomNavigationBar.setMessageBadgeVisibility(true)
        }

        mHomeBottomNavigationBar.setTabSelectedListener(object:BottomNavigationBar.OnTabSelectedListener{
            override fun onTabReselected(position: Int) {

            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabSelected(position: Int) {
                fragmentHelper.switchFragment(mFragmentList[position])
            }
        })
    }

    private fun initFragment(){
        fragmentHelper.switchFragment(mHomeFragment)
        mFragmentList = listOf(mHomeFragment,mCategoryFragment,mCartFragment,mMessageFragment,mUserFragment)
    }


}
