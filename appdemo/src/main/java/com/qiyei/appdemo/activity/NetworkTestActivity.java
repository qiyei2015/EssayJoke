package com.qiyei.appdemo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qiyei.appdemo.R;
import com.qiyei.appdemo.model.DiscoverListResult;
import com.qiyei.appdemo.net.Bean2;
import com.qiyei.appdemo.net.DiscoverListReq;
import com.qiyei.appdemo.net.RequestObject;
import com.qiyei.appdemo.net.ResponseObject;
import com.qiyei.appdemo.net.RetrofitApiService;
import com.qiyei.sdk.https.api.HttpManager;
import com.qiyei.sdk.https.api.listener.IHttpListener;
import com.qiyei.sdk.https.api.request.HttpGetRequest;
import com.qiyei.sdk.https.api.request.HttpPostRequest;
import com.qiyei.sdk.log.LogManager;

/**
 * @author Created by qiyei2015 on 2017/10/28.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class NetworkTestActivity extends AppCompatActivity {

    private final String TAG = "BaseActivity";

    //本地地址
    private final String baseurl = "http://192.168.1.103:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //get请求
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpManager().execute(getSupportFragmentManager(), buildGetRequest(), new IHttpListener<DiscoverListResult>() {

                    @Override
                    public void onSuccess(DiscoverListResult response) {
                        LogManager.d(TAG,"name --> "+response.getData().getCategories().getName());
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        LogManager.d(TAG,exception.getMessage());
                    }
                });
            }
        });

        //post请求
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpManager().execute(getSupportFragmentManager(),buildPostRequest(), new IHttpListener<ResponseObject<Bean2>>() {

                    @Override
                    public void onSuccess(ResponseObject<Bean2> response) {
                        Bean2 bean2 = response.getContent();
                        LogManager.d(TAG,"response --> "+ bean2.toString());
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        LogManager.d(TAG,exception.getMessage());
                    }
                });
            }
        });

        //download请求
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //upload请求
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    /**
     * retrofit get请求
     * @return
     */
    private HttpGetRequest buildGetRequest(){
        DiscoverListReq req = new DiscoverListReq();
        req.setIid("6152551759");
        req.setAid("7");
        HttpGetRequest<DiscoverListReq> request = new HttpGetRequest(req);
        request.setBaseUrl("http://is.snssdk.com/2/essay/");
        request.setPathUrl("discovery/v3/");
        request.setCache(true);
        request.setApiClazz(RetrofitApiService.class);
        return request;
    }

    /**
     * retrofit post请求
     * @return
     */
    private HttpPostRequest buildPostRequest(){

        Bean2 bean = new Bean2("qiyei2016","123456");

        RequestObject<Bean2> req = new RequestObject<>();
        req.setContent(bean);
        HttpPostRequest<RequestObject<Bean2>> request = new HttpPostRequest(req);
        request.setBaseUrl(baseurl);
        request.setPathUrl("user/register");
        request.setCache(true);
        request.setApiClazz(RetrofitApiService.class);
        return request;
    }


}
