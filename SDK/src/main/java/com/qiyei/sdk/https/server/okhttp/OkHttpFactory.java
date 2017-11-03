package com.qiyei.sdk.https.server.okhttp;


import com.qiyei.sdk.https.HTTP;
import com.qiyei.sdk.https.server.HttpTask;
import com.qiyei.sdk.https.server.MyInterceptor;
import com.qiyei.sdk.https.server.ProgressRequestBody;
import com.qiyei.sdk.https.server.ProgressResponseBody;
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

}
