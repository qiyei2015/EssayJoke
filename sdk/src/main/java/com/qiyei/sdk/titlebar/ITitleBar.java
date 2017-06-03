package com.qiyei.sdk.titlebar;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/14.
 * Version: 1.0
 * Description:
 */
public interface ITitleBar {
    /**
     * 头部的布局文件
     * @return
     */
    int bindLayoutId();

    /**
     * 绑定头部的参数，应用View
     */
    void applyView();
}
