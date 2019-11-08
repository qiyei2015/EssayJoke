package com.qiyei.framework.skin.attrs;

import android.view.View;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/10.
 * Version: 1.0
 * Description: 皮肤属性
 */
public class SkinAttrs {
    /**
     * 资源名称
     */
    private String mResName;
    /**
     * 皮肤类型
     */
    private SkinType mSkinType;

    public SkinAttrs(String resName, SkinType skinType) {
        mResName = resName;
        mSkinType = skinType;
    }

    /**
     *
     * @param view
     */
    public void skin(View view){
        mSkinType.skin(view,mResName);
    }

    /**
     * @return {@link #mResName}
     */
    public String getResName() {
        return mResName;
    }

    /**
     * @param resName the {@link #mResName} to set
     */
    public void setResName(String resName) {
        mResName = resName;
    }

    /**
     * @return {@link #mSkinType}
     */
    public SkinType getSkinType() {
        return mSkinType;
    }

    /**
     * @param skinType the {@link #mSkinType} to set
     */
    public void setSkinType(SkinType skinType) {
        mSkinType = skinType;
    }
}
