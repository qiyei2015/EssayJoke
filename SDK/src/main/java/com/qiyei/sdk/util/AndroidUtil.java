package com.qiyei.sdk.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/9/3.
 * Version: 1.0
 * Description:
 */
public class AndroidUtil {

    /**
     * 判断是否是Debug状态
     * @param context
     * @return
     */
    public static boolean isDebug(Context context){
        boolean isDebug = context.getApplicationInfo()!=null&&
                (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        return isDebug;
    }

}
