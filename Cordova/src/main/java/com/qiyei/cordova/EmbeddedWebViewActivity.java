package com.qiyei.cordova;


import android.content.Intent;
import android.os.Bundle;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaInterfaceImpl;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.PluginManager;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;
import org.json.JSONException;

/**
 * @author Created by qiyei2015 on 2018/6/1
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 需要继承CordovaActivity
 */
public class EmbeddedWebViewActivity extends CordovaActivity {

    private static final String TAG = "EmbeddedWebViewActivity";

    private CordovaWebView mWebInterface;

    private CordovaInterfaceImpl mCordovaInterfaceImpl = new CordovaInterfaceImpl(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embedded_web_view);

        //初始化webview
        ConfigXmlParser parser = new ConfigXmlParser();
        parser.parse(this);

        SystemWebView systemWebView = (SystemWebView) findViewById(R.id.system_webview);
        //初始化CordovaWebView对象
        mWebInterface = new CordovaWebViewImpl(new SystemWebViewEngine(systemWebView));
        //初始化mWebInterface
        mWebInterface.init(mCordovaInterfaceImpl,parser.getPluginEntries(),parser.getPreferences());

        //加载默认的url "file:///android_asset/www/index.html";
        systemWebView.loadUrl(parser.getLaunchUrl());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //销毁掉插件管理器
        PluginManager pluginManager = mWebInterface.getPluginManager();
        if (pluginManager != null){
            pluginManager.onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        //处理onActivityResult返回
        mCordovaInterfaceImpl.onActivityResult(requestCode,resultCode,intent);
    }

    /**
     * 权限回调交由 mCordovaInterfaceImpl 处理
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            mCordovaInterfaceImpl.onRequestPermissionResult(requestCode,permissions,grantResults);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
