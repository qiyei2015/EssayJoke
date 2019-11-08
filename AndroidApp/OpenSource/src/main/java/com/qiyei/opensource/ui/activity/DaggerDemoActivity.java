package com.qiyei.opensource.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.qiyei.opensource.R;
import com.qiyei.opensource.data.bean.User;
import com.qiyei.opensource.data.bean.UserModel;
import com.qiyei.opensource.data.bean.WarpUser;
//import com.qiyei.opensource.injection.component.DaggerDaggerDemoComponent;
import com.qiyei.opensource.injection.component.DaggerUserModelComponent;
import com.qiyei.opensource.injection.module.UserModelModule;
import com.qiyei.sdk.util.ToastUtil;

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
//     */
//    @Inject
//    User mUser;
//
//    @Inject
//    WarpUser mWarpUser;

    @Inject
    UserModel mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_demo);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.showLongToast(mUser.getName());
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.showLongToast(mWarpUser.getName());
                mUserModel.getName();
            }
        });
//        DaggerDaggerDemoComponent.create().inject(this);

        DaggerUserModelComponent.builder()
                .userModelModule(new UserModelModule())
                .build()
                .inject(this);
    }
}
