package com.qiyei.sdk.https.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qiyei.sdk.https.api.request.HttpGetRequest;
import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by qiyei2015 on 2017/10/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 功能工具类
 */
public class HttpUtil {

    private static final String TAG = HttpUtil.class.getSimpleName();

    /**
     * 将json解析为字符串
     * @param json
     * @param clazz
     * @param <T>
     */
    public static <T> T parseJson(String json,Class<?> clazz,boolean isInterface){
        if (json == null){
            return null;
        }

        Type genType = null;

        if (isInterface){
            //获取type类型数组的第0个
            genType = clazz.getGenericInterfaces()[0];
        }else {
            genType = clazz.getGenericSuperclass();
        }
        LogManager.d(TAG,"genType:" + genType.toString());

        //判断是不是参数化类型
        if (genType instanceof ParameterizedType){
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            Gson gson = new Gson();
            Class<?> cla = (Class<?>) params[0];
            LogManager.d(TAG,"Class<?>:" + cla);
            T obj = (T) gson.fromJson(json,cla);
            return obj;
        }
        return null;
    }

    /**
     * 获取泛型的参数clazz
     * @param clazz
     * @param isInterface 是否是接口
     */
    public static Class<?>  getParamsClazz(Class<?> clazz,boolean isInterface){

        Type genType = null;

        if (isInterface){
            //获取type类型数组的第0个
            genType = clazz.getGenericInterfaces()[0];
        }else {
            genType = clazz.getGenericSuperclass();
        }
        LogManager.d(TAG,"genType:" + genType.toString());

        //判断是不是参数化类型
        if (genType instanceof ParameterizedType){
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            Class<?> cla = (Class<?>) params[0];
            LogManager.d(TAG,"Class<?>:" + cla);
            return cla;
        }
        return null;
    }


    /**
     * 将 httpGet请求进行参数的序列化为map对象
     * @param request
     * @param <T>
     * @return
     */
    public static <T> Map<String,String> gsonToGetParams(HttpGetRequest<T> request){
        //将对象序列化成字符串
        Class<?> clazz = request.getClass();
        String gsonStr = new Gson().toJson(request.getBody());
        Map<String,String> map = new Gson().fromJson(gsonStr, new TypeToken<HashMap<String,String>>(){}.getType());
        return map;
    }

}
