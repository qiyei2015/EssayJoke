package com.qiyei.framework.inject


import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.ui.activity.BaseActivity
import com.qiyei.framework.ui.activity.BaseMVPActivity
import dagger.Component


/**
 * @author Created by qiyei2015 on 2018/9/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Component
interface ActivityComponent{

    fun inject(activity:BaseActivity)

}