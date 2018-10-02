package com.qiyei.framework.ui.activity

import android.os.Bundle
import com.qiyei.framework.FrameworkApplication
import com.qiyei.framework.injection.component.ActivityComponent
import com.qiyei.framework.injection.component.DaggerActivityComponent
import com.qiyei.framework.injection.module.ActivityModule
import com.qiyei.framework.injection.module.LifecycleProviderModule
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.sdk.https.dialog.LoadingManager
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: MVP基类Activity，持有Presenter
 */
abstract class BaseMVPActivity<T:BasePresenter<*>> :BaseActivity(),IBaseView{
    @Inject
    lateinit var mPresenter:T

    lateinit var mActivityComponet:ActivityComponent

    lateinit var mDialogTAG:String
    /**
     * 标题栏
     */
    protected var mTitleBar: CommonTitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        initComponentInject()
    }

    override fun showLoading() {
        mDialogTAG = this.javaClass.canonicalName
        LoadingManager.showDialog(supportFragmentManager,mDialogTAG)
    }

    override fun hideLoading() {
        LoadingManager.dismissDialog(supportFragmentManager,mDialogTAG)
    }

    override fun onError(text: String) {
        toast(text)
    }

    /**
     * 抽象函数
     */
    abstract fun initComponentInject()

    /**
     * 初始化注入
     */
    private fun initActivityInjection(){
        mActivityComponet = DaggerActivityComponent.builder()
                .appComponent((application as FrameworkApplication).mAppComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()

    }

}