package com.qiyei.performance.hook;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.qiyei.sdk.log.LogManager;

import de.robv.android.xposed.DexposedBridge;
import de.robv.android.xposed.XC_MethodHook;


/**
 * @author Created by qiyei2015 on 2019/11/20.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class BitmapHook {

    private static final String TAG = "BitmapHook";

    static class ImageViewMethodHook extends XC_MethodHook {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            //得到ImageView对象
            ImageView imageView = (ImageView) param.thisObject;
            //得到Bitmap对象
            Bitmap bitmap = (Bitmap) param.args[0];

            LogManager.d(TAG,param.method.getName() + " imageView,width=" + imageView.getWidth() + " height=" + imageView.getHeight()
                    + " bitmap,width=" + bitmap.getWidth() + " height=" + bitmap.getHeight());
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
        }
    }

    public static void start(){
        DexposedBridge.findAndHookMethod(ImageView.class,"setImageBitmap",new ImageViewMethodHook());
    }
}
