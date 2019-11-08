package com.qiyei.framework.mvp.view

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IBaseView {
    /**
     * 显示对话框
     */
    fun showLoading()

    /**
     * 隐藏对话框
     */
    fun hideLoading()

    /**
     * 错误回调
     */
    fun onError(text:String)
}