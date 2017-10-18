package com.qiyei.sdk.https;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.qiyei.sdk.db.DaoSupportFactory;
import com.qiyei.sdk.db.IDaoSupport;
import com.qiyei.sdk.https.cache.HttpCache;
import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/7.
 * Version: 1.0
 * Description:
 */
public class HttpUtil {

    private static final String TAG = "HTTP";

    /**
     * 拼接参数
     * @param url
     * @param params
     * @return
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
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
        LogManager.d(TAG,"genType:" + genType.toString());
        //判断是不是参数化类型
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        LogManager.d(TAG,"params:" + params.toString());
        T obj = (T) gson.fromJson(json,(Class) params[0]);
        return obj;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public static String getCache(String key){
        IDaoSupport<HttpCache> daoSupport = DaoSupportFactory.getInstance().getDao(HttpCache.class);

        //从数据库中去取
        List<HttpCache> caches = daoSupport.querySupport().selection("key = ?")
                .selectionArgs(key).query();

        //有数据
        if (caches != null && caches.size() > 0){
            HttpCache cache = caches.get(0);
            return cache.getValue();
        }
        return null;
    }

    /**
     * 设置缓存
     * @param key
     * @param value
     * @return
     */
    public static long setCache(String key,String value){
        if (TextUtils.isEmpty(key)){
            return 0;
        }

        IDaoSupport<HttpCache> daoSupport = DaoSupportFactory.getInstance().getDao(HttpCache.class);
        daoSupport.delete("key = ?",key);
        return daoSupport.insert(new HttpCache(key,value));
    }

    /**
     * 设置缓存
     * @param cache
     * @return
     */
    public static long setCache(HttpCache cache){
        if (cache != null){
            return 0;
        }
        IDaoSupport<HttpCache> daoSupport = DaoSupportFactory.getInstance().getDao(HttpCache.class);
        daoSupport.delete("key = ?",cache.getKey());
        return daoSupport.insert(cache);
    }

}
