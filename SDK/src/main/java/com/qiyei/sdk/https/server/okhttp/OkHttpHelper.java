package com.qiyei.sdk.https.server.okhttp;


import com.google.gson.Gson;
import com.qiyei.sdk.https.api.HttpRequest;
import com.qiyei.sdk.https.HTTP;
import com.qiyei.sdk.https.server.HttpCallManager;
import com.qiyei.sdk.https.server.HttpTask;
import com.qiyei.sdk.https.server.HttpUtil;
import com.qiyei.sdk.https.server.IHttpTransferCallback;
import com.qiyei.sdk.https.server.ProgressRequestBody;
import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class OkHttpHelper {

    /**
     * JSON类型
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType STREAM = MediaType.parse("application/octet-stream; charset=utf-8");
    /**
     * 创建get请求的url
     * @param request
     * @return
     */
    public static <T> String buildGetRequestUrl(HttpRequest request){

        String url = request.getBaseUrl() + request.getPathUrl();

        Map<String,String> params = HttpUtil.gsonToGetParams(request);

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
     * 创建post请求的url
     * @param request
     * @return
     */
    public static String buildPostRequestUrl(HttpRequest request){
        String url = request.getBaseUrl() + request.getPathUrl();
        return url;
    }


    /**
     * 创建Get 请求
     * @param client
     * @param task
     * @param <T>
     * @return
     */
    public static <T> Call buildGetCall(OkHttpClient client,HttpTask<T> task){
        String url = OkHttpHelper.buildGetRequestUrl(task.getRequest());
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url).tag(task);
        return client.newCall(builder.build());
    }

    /**
     * 创建post 请求
     * @param client
     * @param task
     * @param <T>
     * @return
     */
    public static <T> Call buildPostCall(OkHttpClient client,HttpTask<T> task){
        String url = OkHttpHelper.buildPostRequestUrl(task.getRequest());
        Gson gson = new Gson();
        //转化为gson字符串
        String json = gson.toJson(task.getRequest().getBody());
        RequestBody body = RequestBody.create(JSON, json);

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url).post(body).tag(task);

        return client.newCall(builder.build());
    }


    /**
     * 创建post 请求
     * @param client
     * @param task
     * @param <T>
     * @return
     */
    public static <T> Call buildDownloadCall(OkHttpClient client,HttpTask<T> task){
        String url = OkHttpHelper.buildGetRequestUrl(task.getRequest());
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url).get().tag(task);
        return client.newCall(builder.build());
    }

    /**
     * 创建post 请求
     * @param client
     * @param task
     * @param <T>
     * @return
     */
    public static <T,R> Call buildUploadCall(OkHttpClient client,HttpTask<T> task,IHttpTransferCallback<R> callback){

        String url = OkHttpHelper.buildPostRequestUrl(task.getRequest());

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        //上传使用MultipartBody
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        //需要根据服务器来填写
        bodyBuilder.addFormDataPart("file",task.getRequest().getFilePath(), RequestBody.create(STREAM, task.getRequest().getFilePath()));
        MultipartBody body = bodyBuilder.build();

        builder.post(new ProgressRequestBody(body,callback));

        return client.newCall(builder.build());
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
        LogManager.d(HTTP.TAG,"genType:" + genType.toString());
        //判断是不是参数化类型
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        LogManager.d(HTTP.TAG,"params:" + params.toString());
        T obj = (T) gson.fromJson(json,(Class) params[0]);
        return obj;
    }

}
