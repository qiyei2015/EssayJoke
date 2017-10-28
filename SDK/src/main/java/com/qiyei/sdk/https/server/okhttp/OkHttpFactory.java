package com.qiyei.sdk.https.server.okhttp;


import com.qiyei.sdk.https.HTTP;
import com.qiyei.sdk.https.server.HttpTask;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.TimeUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 创建OkHttpClient的工厂类
 */
public class OkHttpFactory {

    /**
     * 超时时间
     */
    private static final int TIMEOUT = 30;

    /**
     * SSL上下文
     */
    private static SSLContext sslContext;


    static {
//        sslContext = SSLContext.getInstance("SSL");
//        sslContext.init(null, trustAllCerts,
//                new java.security.SecureRandom());
    }

    /**
     * 加密证书
     */
    private static TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(
                X509Certificate[] chain,
                String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(
                X509Certificate[] chain,
                String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            X509Certificate[] x509Certificates = new X509Certificate[0];
            return x509Certificates;
        }
    }};

    /**
     * 加密处理器
     */
    private static X509TrustManager mX509TrustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    /**
     * 拦截器
     */
    private static class MyInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            long id = System.currentTimeMillis();
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();

//            for (String key : headers.keySet()) {
//                requestBuilder.addHeader(key, headers.get(key));
//            }
//            String ip = DnsManager.getInstance().findIpByHost(original.url().host());
//            if (!TextUtils.isEmpty(ip)) {
//                requestBuilder.addHeader("host", original.url().host());
//                requestBuilder.url(original.url().toString().replace(original.url().host(), ip));
//            }

            Request request = requestBuilder.build();
            //利用okHttp 的tag来保存数据
            HttpTask task = (HttpTask) request.tag();

            String taskId = task.getTaskId();

            long requestTime = System.currentTimeMillis();

            if (request.method().contains("GET")) {

                LogManager.i(HTTP.TAG, getRequestInfo(request,taskId,System.currentTimeMillis()));
            } else {
                RequestBody rb = request.body();
                if (rb != null) {
                    okio.Buffer buffer = new okio.Buffer();
                    rb.writeTo(buffer);
                    LogManager.i(HTTP.TAG, getRequestInfo(request,taskId,System.currentTimeMillis()) + "\nbody = " + buffer.readUtf8());
                    buffer.clear();
                } else {
                    LogManager.i(HTTP.TAG, getRequestInfo(request,taskId,System.currentTimeMillis()));
                }
            }
            okhttp3.Response response = chain.proceed(request);
            String body = response.body().string();

            LogManager.i(HTTP.TAG, getResponseInfo(taskId,requestTime,System.currentTimeMillis()) + "\nbody: " +  body);

            okhttp3.MediaType mediaType = response.body().contentType();
            return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, body)).build();
        }
    }

    /**
     * 创建OkHttpClient
     * @return
     */
    public static OkHttpClient createOkHttpClient(){
        try {
            sslContext = SSLContext.getDefault();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new MyInterceptor())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslContext.getSocketFactory(),mX509TrustManager)
                .retryOnConnectionFailure(false)
                .build();
        return okHttpClient;

    }

    /**
     * 请求信息整理
     * @param request
     * @param taskId
     * @param time
     * @return
     */
    private static String getRequestInfo(Request request,String taskId,long time){
        String requestInfo = "Request ---> time: " + TimeUtil.formatTime(time,TimeUtil.FORMAT_1)
                + " id: " + taskId  + " url = " + request.url();

        return requestInfo;
    }

    /**
     * 响应信息格式
     * @param taskId
     * @param requestTime
     * @param time
     * @return
     */
    private static String getResponseInfo(String taskId,long requestTime,long time){
        String responseInfo = "Response --> time: " + TimeUtil.formatTime(time,TimeUtil.FORMAT_1)
                + " id: "  + taskId + " " + (System.currentTimeMillis() - requestTime) + "ms";
        return responseInfo;
    }

}
