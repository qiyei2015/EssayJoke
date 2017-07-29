package com.qiyei.framework.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import com.qiyei.sdk.log.LogUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/9.
 * Version: 1.0
 * Description:
 */
public class SkinResources {

    private static final String TAG = SkinManager.TAG;
    /**
     * 资源管理
     */
    private Resources mResources;
    /**
     * 包名
     */
    private String mPackageName;

    public SkinResources(Context context,String path){

        //读取本地的一个 .skin里面的资源
        Resources superRes = context.getResources();

        try {
            //创建AssetManager
            AssetManager assetManager = AssetManager.class.newInstance();

            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.setAccessible(true);

            //反射执行方法
            method.invoke(assetManager,path);

            mResources = new Resources(assetManager,superRes.getDisplayMetrics(),superRes.getConfiguration());

            //获取skinPath包名
            mPackageName = context.getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)
                    .packageName;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据名字获取颜色值
     * @param name
     * @return
     */
    public ColorStateList getColorByName(String name){
        try {
            int resId = mResources.getIdentifier(name,"color", mPackageName);
            ColorStateList colorStateList;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                colorStateList = mResources.getColorStateList(resId,null);
            }else {
                colorStateList = mResources.getColorStateList(resId);
            }

            LogUtil.d(TAG,"resId -> " + resId + " mPackageName -> " + mPackageName  + " resName -> " + name);
            return colorStateList;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            LogUtil.d(TAG," mPackageName -> " + mPackageName  + " resName -> " + name);
            return null;
        }
    }

    /**
     * 根据名字获取Drawable
     * @param name
     * @return
     */
    public Drawable getDrawableByName(String name){
        try {
            int resId = mResources.getIdentifier(name,"drawable", mPackageName);
            Drawable drawable = mResources.getDrawable(resId,null);
            LogUtil.d(TAG,"resId -> " + resId + " mPackageName -> " + mPackageName  + " resName -> " + name);
            return drawable;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            LogUtil.d(TAG," mPackageName -> " + mPackageName  + " resName -> " + name);
            return null;
        }
    }

}
