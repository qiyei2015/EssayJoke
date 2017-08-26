package com.qiyei.essayjoke.activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.qiyei.essayjoke.R;
import com.qiyei.essayjoke.model.User;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.sdk.db.DaoSupportFactory;
import com.qiyei.sdk.db.IDaoSupport;
import com.qiyei.sdk.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/24.
 * Version: 1.0
 * Description: app启动的欢迎界面
 */
public class WelcomeActivity extends BaseSkinActivity {
    /**
     * 用作启动延迟
     */
    private Handler mHandler;
    /**
     * 超时时间
     */
    private static final int TIME_OUT = 2000;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initData() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //跳转到主页
                startActivity(HomeActivity.class);
                finish();
            }
        },TIME_OUT);
    }

    @Override
    protected void initView() {
//如果没有权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_WRITE_STORE);
        }else {
            initDataBase();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        mHandler = null;
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORE){
            LogManager.d(TAG,"onRequestPermissionsResult,size:" + grantResults.length);

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                initDataBase();
            }else {
                ToastUtil.showLongToast("你拒绝了获取存储卡的权限");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 初始化数据库
     */
    private void initDataBase(){
        IDaoSupport<User> userDao = DaoSupportFactory.getInstance().getDao(User.class);
        List<User> users = new ArrayList<>();
        for (int i = 0;i < 100;i++){
            users.add(new User("qiyei2015",i+1));
        }
        userDao.insert(users);
    }

}
