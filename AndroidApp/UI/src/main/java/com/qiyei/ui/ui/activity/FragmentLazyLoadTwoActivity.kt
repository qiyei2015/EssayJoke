package com.qiyei.ui.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qiyei.ui.R
import com.qiyei.ui.ui.adapter.FragmentLazyLoadTwoPagerAdapter
import com.qiyei.ui.ui.fragment.FragmentLazyLoadOne
import com.qiyei.ui.ui.fragment.FragmentLazyLoadTwo
import com.qiyei.ui.ui.fragment.loadFragments
import kotlinx.android.synthetic.main.activity_fragment_lazy_load_two.*

/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class FragmentLazyLoadTwoActivity : AppCompatActivity() {

    companion object {
        class MyFragment(name:String) : FragmentLazyLoadOne(name){

            override fun lazyInit() {

            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_lazy_load_two)

        loadFragments(R.id.content, 0, supportFragmentManager, MyFragment("懒加载方式2"))

        viewPager.adapter = FragmentLazyLoadTwoPagerAdapter(supportFragmentManager, FragmentLazyLoadTwoPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

    }
}