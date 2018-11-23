package com.qiyei.framework.ui.fragment

import android.os.Bundle
import android.view.View
import com.qiyei.framework.FrameworkApplication
import com.qiyei.framework.injection.component.ActivityComponent
import com.qiyei.framework.injection.component.DaggerActivityComponent
import com.qiyei.framework.injection.module.ActivityModule
import com.qiyei.framework.injection.module.LifecycleProviderModule
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.sdk.https.dialog.LoadingManager
import com.qiyei.sdk.log.LogManager
import com.qiyei.sdk.util.UUIDUtil
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/9/26.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
abstract class BaseMVPFragment<T:BasePresenter<*>>:BaseFragment(),IBaseView {

    @Inject
    lateinit var mPresenter:T

    lateinit var mActivityComponent: ActivityComponent

    private var mDialogTAG:String? = null

    private var mDialogPre = ""

    /**
     * 标题栏
     */
    protected var mTitleBar: CommonTitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        initComponentInject()
        mDialogPre = this.javaClass.simpleName + "_"
    }

    override fun showLoading() {
        if (mDialogTAG == null){
            mDialogTAG = mDialogPre + UUIDUtil.get16UUID()
            LogManager.i(getTAG(), "${LoadingManager.TAG} showLoading tag:$mDialogTAG")
            LoadingManager.showDialog(childFragmentManager,mDialogTAG)
        }else {
            LogManager.i(getTAG(),"${LoadingManager.TAG} showLoading error:already has a dialog")
        }
    }

    override fun hideLoading() {
        if (mDialogTAG != null){
            LogManager.i(getTAG(), "${LoadingManager.TAG} hideLoading tag:$mDialogTAG")
            LoadingManager.dismissDialog(childFragmentManager,mDialogTAG)
            mDialogTAG = null
        }else {
            LogManager.i(getTAG(),"${LoadingManager.TAG} hideLoading error,tag is null")
        }
    }

    override fun onError(text: String) {
        toast(text)
    }

    override fun onClick(view: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
//        if (Bu)
    }

    /**
     * 抽象函数
     */
    abstract fun initComponentInject()

    /**
     * 初始化注入
     */
    private fun initActivityInjection(){
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((activity!!.application as FrameworkApplication).mAppComponent)
                .activityModule(ActivityModule(activity!!))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()

    }

}