package qiyei.com.appdemo.activity;


import android.os.Bundle;
import android.view.View;

import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.framework.titlebar.CommonTitleBar;
import com.qiyei.sdk.http.HttpManager;
import com.qiyei.sdk.http.base.HttpRequest;
import com.qiyei.sdk.http.base.INetCallback;
import com.qiyei.sdk.http.base.RequestMethod;
import com.qiyei.sdk.log.LogUtil;
import com.qiyei.sdk.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import qiyei.com.appdemo.R;
import qiyei.com.appdemo.model.DiscoverListResult;

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
        new HttpManager().execute(getSupportFragmentManager(),buildRequest(), new INetCallback<DiscoverListResult>() {
            @Override
            public void onSuccess(DiscoverListResult result) {
                LogUtil.d(TAG,"name --> "+result.getData().getCategories().getName());
                //ToastUtil.showLongToast(result.getData().getCategories().getName());
            }

            @Override
            public void onFail(Exception e) {
                LogUtil.d(TAG,e.getMessage());
                ToastUtil.showLongToast(e.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void addCommonParams(Map<String,Object> params){
        params.put("app_name","joke_essay");
        params.put("version_name","5.7.0");
        params.put("ac","wifi");
        params.put("device_id","30036118478");
        params.put("device_brand","Xiaomi");
        params.put("update_version_code","5701");
        params.put("manifest_version_code","570");
        params.put("longitude","113.000366");
        params.put("latitude","28.171377");
        params.put("device_platform","android");
    }


    private HttpRequest buildRequest(){

        HttpRequest request = new HttpRequest();

        request.setUrl("http://is.snssdk.com/2/essay/discovery/v3/");
        Map<String,Object> params = new HashMap<>();

        params.put("iid","6152551759");
        params.put("aid","7");

        addCommonParams(params);

        request.setParams(params);
        request.setRequestMethod(RequestMethod.GET);
        request.setUseCache(true);

        return request;
    }
}
