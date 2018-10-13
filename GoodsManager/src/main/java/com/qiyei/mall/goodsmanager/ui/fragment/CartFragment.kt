package com.qiyei.mall.ordermanager.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.fragment.BaseMVPFragment
import com.qiyei.mall.goodsmanager.R
import com.qiyei.mall.ordermanager.injection.component.DaggerCartComponent


import com.qiyei.mall.ordermanager.mvp.presenter.CartManagerPresenter
import com.qiyei.mall.ordermanager.mvp.view.ICartManagerView
import com.qiyei.sdk.log.LogManager
import org.jetbrains.anko.support.v4.toast


/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CartFragment : BaseMVPFragment<CartManagerPresenter>(),ICartManagerView {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogManager.i(getTAG(),"onViewCreated")
        initView()
    }

    /**
     * 依赖注入
     */
    override fun initComponentInject() {
        DaggerCartComponent.builder()
                .activityComponent(mActivityComponent)
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.common_cart))
                .setRightText(getString(R.string.common_edit))
                .setRightClickListener {
                    toast("编辑")
                }
                .build()
    }

}
