package com.qiyei.ui.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qiyei.ui.R
import com.qiyei.ui.ui.adapter.FragmentLazyLoadOnePagerAdapter
import com.qiyei.ui.ui.adapter.FragmentLazyLoadTwoPagerAdapter
import com.qiyei.ui.ui.fragment.FragmentLazyLoadOne
import kotlinx.android.synthetic.main.activity_fragment_lazy_load_two.*

/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class FragmentLazyLoadOneActivity : AppCompatActivity() {

    companion object {
        class MyFragment(name:String) : FragmentLazyLoadOne(name){

            override fun lazyInit() {

            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_lazy_load_one)
        supportFragmentManager.beginTransaction().apply {
            add(R.id.content,MyFragment("懒加载实现1"))
        }.commit()

        viewPager.adapter = FragmentLazyLoadOnePagerAdapter(supportFragmentManager, FragmentLazyLoadTwoPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

    }
}