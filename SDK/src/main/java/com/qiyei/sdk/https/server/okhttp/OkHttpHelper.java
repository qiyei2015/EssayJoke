package com.qiyei.sdk.https.server.okhttp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.google.gson.Gson;
import com.qiyei.sdk.https.api.request.HttpGetRequest;
import com.qiyei.sdk.https.base.Https;
import com.qiyei.sdk.https.dialog.LoadingDialog;
import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class OkHttpHelper {


    /**
     * 创建get请求的url
     * @param request
     * @return
     */
    public static String buildGetRequest(HttpGetRequest request){

        String url = request.getBaseUrl() + request.getPathUrl();

        Map<String,String> params = request.getParams();

        if (params == null || (params!=null && params.size() < 0)){
            return url;
        }

        StringBuffer buffer = new StringBuffer(url);

        if (!url.contains("?")){
            buffer.append("?");
        }else {
            if (!url.endsWith("?")){
                buffer.append("&");
            }
        }

        for (Map.Entry<String,String> entity : params.entrySet()){
            buffer.append(entity.getKey()).append("=").append(entity.getValue()).append("&");
        }

        buffer.deleteCharAt(buffer.length() - 1);

        return buffer.toString();
    }


    /**
     * 显示对话框
     */
    public static void showDialog(FragmentManager fragmentManager,String tag){
        if (fragmentManager == null){
            return;
        }
        LoadingDialog dialog = new LoadingDialog();
        dialog.setCancelable(false);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(dialog, tag);
        fragmentTransaction.commitNowAllowingStateLoss();//立即执行
    }

    /**
     * 取消对话框显示
     */
    public static void dismissDialog(FragmentManager fragmentManager,String tag){
        if (fragmentManager == null){
            return;
        }
        LoadingDialog dialog = (LoadingDialog) fragmentManager.findFragmentByTag(tag);
        if(dialog != null){
            dialog.dismissAllowingStateLoss();
        }
    }


    /**
     * 将json解析为字符串
     * @param json
     * @param clazz
     * @param <T>
     */
    public static <T> T parseJson(String json,Class<T> clazz){
        if (json == null){
            return null;
        }

        Gson gson = new Gson();
        //获取type类型数组的第0个
        Type genType = clazz.getGenericInterfaces()[0];
        LogManager.d(Https.TAG,"genType:" + genType.toString());
        //判断是不是参数化类型
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        LogManager.d(Https.TAG,"params:" + params.toString());
        T obj = (T) gson.fromJson(json,(Class) params[0]);
        return obj;
    }

}
