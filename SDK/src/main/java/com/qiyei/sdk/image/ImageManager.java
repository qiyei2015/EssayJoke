package com.qiyei.sdk.image;

import android.content.Context;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/19.
 * Version: 1.0
 * Description: 图片管理器
 */
public class ImageManager {

    private static Context sContext ;

    /**
     * 图片操作管理者
     */
    private static IImageOper sImageManager;

    /**
     * 初始化方法
     * @param context
     * @param oper
     */
    public static void init(Context context , IImageOper oper){
        sContext = context.getApplicationContext();
        sImageManager = oper;
    }



}
