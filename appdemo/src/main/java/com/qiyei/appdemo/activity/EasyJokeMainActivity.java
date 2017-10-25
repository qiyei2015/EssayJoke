package com.qiyei.appdemo.activity;


import android.os.Bundle;
import android.view.View;

import com.qiyei.appdemo.net.DiscoverListReq;
import com.qiyei.appdemo.net.RetrofitApiService;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.framework.titlebar.CommonTitleBar;
import com.qiyei.sdk.https.api.HttpManager;
import com.qiyei.sdk.https.api.listener.IHttpListener;
import com.qiyei.sdk.https.api.request.HttpGetRequest;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.ToastUtil;

import com.qiyei.appdemo.R;
import com.qiyei.appdemo.model.DiscoverListResult;

public class EasyJokeMainActivity extends BaseSkinActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initData();
        initView();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_easy_joke_main);
    }

    @Override
    protected void initView() {
        CommonTitleBar commonNavigationBar = new CommonTitleBar.Builder(this)
                .setTitle("主界面")
                .setRightText("投稿")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("点击了右边");
                    }
                })
                .build();
    }

    @Override
    protected void initData() {
//        new HttpManager().execute(getSupportFragmentManager(),buildRequest(), new INetCallback<DiscoverListResult>() {
//            @Override
//            public void onSuccess(DiscoverListResult result) {
//                LogManager.d(TAG,"name --> "+result.getData().getCategories().getName());
//                //ToastUtil.showLongToast(result.getData().getCategories().getName());
//            }
//
//            @Override
//            public void onFail(Exception e) {
//                LogManager.d(TAG,e.getMessage());
//                ToastUtil.showLongToast(e.getMessage());
//            }
//        });

        new HttpManager().execute(getSupportFragmentManager(), buildRequest(), new IHttpListener<DiscoverListResult>() {

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

    @Override
    public void onClick(View v) {

    }

//    private void addCommonParams(Map<String,String> params){
//        params.put("app_name","joke_essay");
//        params.put("version_name","5.7.0");
//        params.put("ac","wifi");
//        params.put("device_id","30036118478");
//        params.put("device_brand","Xiaomi");
//        params.put("update_version_code","5701");
//        params.put("manifest_version_code","570");
//        params.put("longitude","113.000366");
//        params.put("latitude","28.171377");
//        params.put("device_platform","android");
//    }
//
//
//
//    private HttpRequest buildRequest(){
//
//        HttpGetRequest request = new HttpGetRequest(null);
//        request.setBaseUrl("http://is.snssdk.com/2/essay/");
//
//        request.setPathUrl("discovery/v3/");
//        Map<String,String> params = new HashMap<>();
//
//        params.put("iid","6152551759");
//        params.put("aid","7");
//
//        addCommonParams(params);
//        request.setParams(params);
//        request.setCache(true);
//        return request;
//    }

    /**
     * retrofit请求
     * @return
     */
    private HttpGetRequest buildRequest(){
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

}
