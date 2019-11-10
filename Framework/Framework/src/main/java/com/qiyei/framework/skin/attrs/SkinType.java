package com.qiyei.framework.skin.attrs;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiyei.framework.skin.SkinManager;
import com.qiyei.framework.skin.SkinResources;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/10.
 * Version: 1.0
 * Description: 皮肤类型，一般就textColor,background,src等属性
 */
public enum  SkinType {

    TEXT_COLOR("textColor")
    ,BACKGROUND("background")
    ,SRC("src");

    /**
     * 名字
     */
    protected String mName;

    /**
     * 构造方法
     * @param name
     */
    SkinType(String name){
        mName = name;
    }

    /**
     * 子类实现
     * @param view
     * @param resName
     */
    public void skin(View view, String resName){
        if (TextUtils.isEmpty(resName) || view == null){
            return;
        }

        SkinResources skinResources = getSkinResources();
        switch (this){
            case TEXT_COLOR:
                ColorStateList color = skinResources.getColorByName(resName);
                if (color == null || !(view instanceof TextView)){
                    return;
                }

                TextView textView = (TextView) view;
                textView.setTextColor(color);
                break;

            case BACKGROUND:
                //注意背景图片也可能是颜色的xml组成
                Drawable background = skinResources.getDrawableByName(resName);
                if (background != null){
                    view.setBackground(background);
                    //return;
                }

                //设置颜色
                ColorStateList backgroundColor = skinResources.getColorByName(resName);
                if (backgroundColor != null){
                    view.setBackgroundColor(backgroundColor.getDefaultColor());
                }
                break;

            case SRC:
                //获取资源Drawable
                Drawable drawable = skinResources.getDrawableByName(resName);
                if (drawable == null || !(view instanceof ImageView)){
                    return;
                }

                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable(drawable);
                break;

            default:
                break;
        }
    }

    /**
     * @return {@link #mName}
     */
    public String getName() {
        return mName;
    }

    /**
     *
     * @return
     */
    public SkinResources getSkinResources(){
        return SkinManager.getInstance().getSkinResources();
    }

}
