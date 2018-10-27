package com.qiyei.mall.messagemanager.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.qiyei.framework.ui.fragment.BaseMVPFragment
import com.qiyei.mall.messagemanager.R
import com.qiyei.mall.messagemanager.injection.component.DaggerMessageManagerComponent
import com.qiyei.mall.messagemanager.mvp.presenter.MessageManagerPresenter
import com.qiyei.mall.messagemanager.mvp.view.IMessageManagerView
import com.qiyei.provider.event.UpdateMessageEvent


/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class MessageFragment : BaseMVPFragment<MessageManagerPresenter>(),IMessageManagerView {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.getString()
    }

    /**
     * 依赖注入
     */
    override fun initComponentInject() {
        DaggerMessageManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun getTAG(): String {
        return MessageFragment::class.java.simpleName
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //点进去说明已经阅读了
        if (!hidden){
            Bus.send(UpdateMessageEvent(false))
        }
    }
}
