package com.qiyei.mall.messagemanager.ui.adapter

import android.content.Context
import com.qiyei.framework.extend.loadUrl
import com.qiyei.mall.messagemanager.R
import com.qiyei.mall.messagemanager.data.bean.Message
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder
import de.hdodenhof.circleimageview.CircleImageView

/**
 * @author Created by qiyei2015 on 2018/10/27.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class MessageListAdapter(context: Context,list: MutableList<Message>):BaseRecyclerAdapter<Message>(context,list,
        R.layout.layout_message_list_item) {

    override fun convert(holder: BaseViewHolder?, item: Message?, position: Int) {
        if (item == null){
            return
        }
        //消息图标
//        holder?.let {
//            it.getView<CircleImageView>(R.id.mMsgIconIv).loadUrl(item.msgIcon)
//        }
        //消息标题
        holder?.setText(R.id.mMsgTitleTv,item.msgTitle)
        //消息内容
        holder?.setText(R.id.mMsgContentTv,item.msgContent)
        //消息时间
        holder?.setText(R.id.mMsgTimeTv,item.msgTime)
    }

}