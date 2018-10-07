package com.qiyei.mall.goodsmanager.common

import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.EditText
import com.kennyc.view.MultiStateView
import com.qiyei.mall.goodsmanager.R
import org.jetbrains.anko.find
import ren.qinc.numberbutton.NumberButton

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */

fun MultiStateView.startLoading() {
    viewState = MultiStateView.VIEW_STATE_LOADING
    val loadingView = getView(MultiStateView.VIEW_STATE_LOADING)
    val animBackground = loadingView!!.find<View>(R.id.loading_anim_view).background
    (animBackground as AnimationDrawable).start()
}


/**
 * 三方控件扩展
 */
fun NumberButton.getEditText(): EditText {
    return find(R.id.text_count)
}