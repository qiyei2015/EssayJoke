package com.qiyei.framework.skin.attrs;

import android.view.View;

import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/10.
 * Version: 1.0
 * Description:
 */
public class SkinView {
    /**
     * 引用的View
     */
    private View mView;
    /**
     * 属性
     */
    private List<SkinAttrs> mSkinAttrsList;

    public SkinView(View view, List<SkinAttrs> skinAttrsList) {
        mView = view;
        mSkinAttrsList = skinAttrsList;
    }

    /**
     *
     */
    public void skin(){
        for (SkinAttrs attrs : mSkinAttrsList){
            attrs.skin(mView);
        }
    }
}
