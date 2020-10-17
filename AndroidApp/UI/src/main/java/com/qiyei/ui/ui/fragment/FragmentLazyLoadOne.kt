package com.qiyei.ui.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qiyei.ui.R
import kotlinx.android.synthetic.main.fragment_lazy_load_two.*


/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: https://andyjennifer.com/2020/01/19/Androidx%E4%B8%8BFragment%E7%9A%84%E6%87%92%E5%8A%A0%E8%BD%BD/
 */
abstract class FragmentLazyLoadOne(val name: String) : Fragment() {

    companion object {
        private const val TAG = "LazyOneFragment"
    }

    private var isLoad = false

    /**
     * 当前fragment对用户是否可见
     */
    private var isVisibleToUser = false

    /**
     * 当使用ViewPager+Fragment形式会调用该方法时，setUserVisibleHint会优先Fragment生命周期函数调用，
     * 所以这个时候就,会导致在setUserVisibleHint方法执行时就执行了懒加载，
     * 而不是在onResume方法实际调用的时候执行懒加载。所以需要这个变量
     */
    private var isCallFromResume = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lazy_load_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv.text = name
    }

    override fun onResume() {
        super.onResume()
        isCallFromResume = true
        judgeLazyInit()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isVisibleToUser = !hidden
        judgeLazyInit()
    }

    /**
     * ViewPager + Fragment中会调用该方法
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        judgeLazyInit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoad = false
        isVisibleToUser = false
        isCallFromResume = false
    }

    private fun judgeLazyInit(){
        if (!isLoad && isVisibleToUser && isCallFromResume){
            lazyInit()
            Log.d(TAG, "lazyInit:!!!!!!!")
            isLoad = true
        }
    }

    /**
     * 网络请求，数据查询等操作
     */
    abstract fun lazyInit()
}