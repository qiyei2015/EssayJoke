package com.qiyei.ui.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qiyei.ui.R
import kotlinx.android.synthetic.main.fragment_lazy_load_two.*

abstract class FragmentLazyLoadTwo(val name: String) : Fragment() {

    companion object {
        private const val TAG = "LazyOneFragment"
    }

    private var isLoad = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lazy_load_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv.text = name
    }

    override fun onResume() {
        super.onResume()
        judgeLazyInit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoad = false
    }


    private fun judgeLazyInit(){
        if (!isLoad ){
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