package com.qiyei.ui.ui.activity;


import android.os.Bundle;
import android.view.View;




import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.framework.data.api.IEassyJokeApiService;
import com.qiyei.framework.data.protocol.DiscoverListReq;
import com.qiyei.framework.data.protocol.DiscoverListResp;
import com.qiyei.framework.titlebar.CommonTitleBar;
import com.qiyei.sdk.https.api.HttpManager;
import com.qiyei.sdk.https.api.HttpRequest;
import com.qiyei.sdk.https.api.IHttpListener;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.ToastUtil;
import com.qiyei.ui.R;



/**
 * @author Created by qiyei2015 on 2017/9/28.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class EssayJokeMainActivity extends BaseSkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initData();
        initView();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_essay_joke_main);
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
        new HttpManager().execute(getSupportFragmentManager(),buildRequest(), new IHttpListener<DiscoverListResp>() {

            @Override
            public void onSuccess(DiscoverListResp response) {
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

    /**
     * retrofit请求
     * @return
     */
    private HttpRequest buildRequest(){
        DiscoverListReq req = new DiscoverListReq();
        req.setIid("6152551759");
        req.setAid("7");
        HttpRequest<DiscoverListReq> request = new HttpRequest.Builder<DiscoverListReq>()
                .get()
                .setBaseUrl("http://is.snssdk.com/2/essay/")
                .setPathUrl("discovery/v3/")
                .setBody(req)
                .setApiClazz(IEassyJokeApiService.class)
                .build();
        return request;
    }

}
