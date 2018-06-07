package com.qiyei.appdemo.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qiyei.appdemo.R;
import com.qiyei.appdemo.dagger.DaggerDaggerComponent;
import com.qiyei.appdemo.dagger.UserModel;

import javax.inject.Inject;


/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class DaggerDemoActivity extends AppCompatActivity {

    /**
     * 需要包级可见
     */
    @Inject
    UserModel mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerDaggerComponent.create().inject(this);

        setContentView(R.layout.activity_dagger_demo);

        mUserModel.getName();

    }


}
