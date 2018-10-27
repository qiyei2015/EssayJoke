package com.qiyei.mall.messagemanager.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.ViewFlipper
import com.qiyei.mall.messagemanager.R
import org.jetbrains.anko.dimen
import org.jetbrains.anko.find
import org.jetbrains.anko.px2sp

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class NewsViewFlipper @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val mViewFlipper:ViewFlipper

    init {
        val rootView = View.inflate(context, R.layout.layout_news_flipper,null)
        mViewFlipper = rootView.find(R.id.mFlipperView)
        mViewFlipper.setInAnimation(context,R.anim.news_bootom_in)
        mViewFlipper.setOutAnimation(context,R.anim.news_bootom_out)

        addView(rootView)
    }

    private fun buildText(data:String):View{
        val textView = TextView(context)
        textView.text = data
        textView.textSize = px2sp(dimen(R.dimen.text_small_size))
        textView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        return textView
    }

    /**
     * 设置显示的数据
     */
    fun setData(datas:Array<String>){
        for (item in datas){
            mViewFlipper.addView(buildText(item))
        }
        mViewFlipper.startFlipping()
    }

}