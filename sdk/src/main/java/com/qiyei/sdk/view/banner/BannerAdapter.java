package com.qiyei.sdk.view.banner;

import android.view.View;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/7/15.
 * Version: 1.0
 * Description:
 */
public abstract class BannerAdapter {

    /**
     * 获取对应位置的Banner描述
     * @param position
     * @return
     */
    public String getBannerDesc(int position){
        return "";
    }

    /**
     * 获取View
     * @param position
     * @return
     */
    public abstract View getView(int position);

    /**
     * 返回数量
     * @return
     */
    public abstract int getCount();

}
