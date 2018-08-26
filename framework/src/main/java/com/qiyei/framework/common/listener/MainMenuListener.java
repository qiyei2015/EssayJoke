package com.qiyei.framework.common.listener;

import android.view.View;

import com.qiyei.framework.common.model.MainMenu;

/**
 * @author Created by qiyei2015 on 2018/8/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface MainMenuListener {

    /**
     * 点击事件
     * @param v
     * @param item
     * @param position
     */
    void click(View v , MainMenu item, int position);

}
