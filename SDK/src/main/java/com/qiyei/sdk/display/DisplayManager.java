package com.qiyei.sdk.display;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.log.LogManager;

/**
 * @author Created by qiyei2015 on 2018/6/12.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 显示管理，用于多屏幕，多分辨率适配
 */
public class DisplayManager {

    private static final String TAG = "DisplayManager";

    private static Context mContext = RuntimeEnv.appContext;

    private static float BASE_SCREEN_WIDTH_FLOAT = 1080f;
    private static float BASE_SCREEN_HEIGHT_FLOAT = 1920f;


    /**
     * 获取设备显示信息
     */
    public static void getDisplayInfo(){
        Resources resources = mContext.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float density = displayMetrics.density;
        int densityDpi = displayMetrics.densityDpi;
        LogManager.i(TAG,"density:" + density + " densityDpi:" + densityDpi);
    }

    public static void scaleView(View view) {
        if (null != view) {
            int paddingLeft = getScaleValue(view.getPaddingLeft());
            int paddingTop = getScaleValue(view.getPaddingTop());
            int paddingRight = getScaleValue(view.getPaddingRight());
            int paddingBottom = getScaleValue(view.getPaddingBottom());
            view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

            if (null != layoutParams) {

                if (layoutParams.width > 0) {
                    layoutParams.width = getScaleValue(layoutParams.width);
                }

                if (layoutParams.height > 0) {
                    layoutParams.height = getScaleValue(layoutParams.height);
                }

                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    int topMargin = getScaleValue(marginLayoutParams.topMargin);
                    int leftMargin = getScaleValue(marginLayoutParams.leftMargin);
                    int bottomMargin = getScaleValue(marginLayoutParams.bottomMargin);
                    int rightMargin = getScaleValue(marginLayoutParams.rightMargin);
                    marginLayoutParams.topMargin = topMargin;
                    marginLayoutParams.leftMargin = leftMargin;
                    marginLayoutParams.bottomMargin = bottomMargin;
                    marginLayoutParams.rightMargin = rightMargin;
                }
            }
            view.setLayoutParams(layoutParams);
        }
    }

    private static int getScaleValue(int ori){
        return 0;
    }

    private static int getWidthScaleValue(){

        return 0;
    }


    private static int getHeightScaleValue(){

        return 0;
    }


    /**
     * 宽的缩放比
     * @return
     */
    private static float getWidthScale(){
        Resources resources = mContext.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        return (float)widthPixels / BASE_SCREEN_WIDTH_FLOAT;
    }

    /**
     * 高的缩放比
     * @return
     */
    private static float getHeightScale(){
        Resources resources = mContext.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        return (float)heightPixels / BASE_SCREEN_HEIGHT_FLOAT;
    }


    private void getDrawableFolderDensity(String imageName,String packageName){
        TypedValue typedValue = new TypedValue();
        Resources resources  = mContext.getResources();
        int id = mContext.getResources().getIdentifier(imageName, "drawable" , packageName);
        resources.openRawResource(id, typedValue);
        int density=typedValue.density;
        System.out.println("----> density="+density);
    }


}
