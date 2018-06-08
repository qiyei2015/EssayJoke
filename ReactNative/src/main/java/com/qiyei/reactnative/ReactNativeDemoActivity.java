package com.qiyei.reactnative;

import com.facebook.react.ReactActivity;

import javax.annotation.Nullable;

/**
 * @author Created by qiyei2015 on 2018/6/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ReactNativeDemoActivity extends ReactActivity{

    private static final String MAIN_COMPONENT = "EssayJokeReactNativeDemo";


    /**
     * 返回在index.android.js 中注册的组件名
     * @return
     */
    @Nullable
    @Override
    protected String getMainComponentName() {
        return MAIN_COMPONENT;
    }

}
