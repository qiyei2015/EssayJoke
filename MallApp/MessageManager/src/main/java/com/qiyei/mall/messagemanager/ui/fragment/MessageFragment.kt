package com.qiyei.mall.messagemanager.ui.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.fragment.BaseMVPFragment
import com.qiyei.mall.messagemanager.R
import com.qiyei.mall.messagemanager.data.bean.Message
import com.qiyei.mall.messagemanager.injection.component.DaggerMessageManagerComponent
import com.qiyei.mall.messagemanager.injection.module.MessageManagerModule
import com.qiyei.mall.messagemanager.mvp.presenter.MessageManagerPresenter
import com.qiyei.mall.messagemanager.mvp.view.IMessageManagerView
import com.qiyei.mall.messagemanager.ui.adapter.MessageListAdapter
import com.qiyei.provider.event.UpdateMessageEvent
import com.qiyei.sdk.log.LogManager
import kotlinx.android.synthetic.main.fragment_message.*


/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class MessageFragment : BaseMVPFragment<MessageManagerPresenter>(),IMessageManagerView {

    /***
     * 消息列表Adapter
     */
    private lateinit var mAdapter:MessageListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    /**
     * 依赖注入
     */
    override fun initComponentInject() {
        DaggerMessageManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .messageManagerModule(MessageManagerModule())
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

    override fun onMessageListCallback(list: MutableList<Message>?) {
        LogManager.i(getTAG(),"onMessageListCallback,list:$list")
        if (list == null || list.size == 0){
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            mAdapter.datas = list
        }
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.message_center))
                .setLeftViewVisible(false)
                .build()

        mMessageListRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = MessageListAdapter(context!!, arrayListOf())
        mMessageListRecyclerView.adapter = mAdapter
    }

    private fun loadData(){
        mPresenter.getMessageList()
    }
}
