package com.qiyei.appdemo.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.qiyei.appdemo.model.MainMenu;
import com.qiyei.appdemo.ui.activity.BannerTestActivity;
import com.qiyei.appdemo.ui.activity.BinderTestActivity;
import com.qiyei.appdemo.ui.activity.DaggerDemoActivity;
import com.qiyei.appdemo.ui.activity.DataCenterTestActivity;
import com.qiyei.appdemo.ui.activity.DatabaseTestActivity;
import com.qiyei.appdemo.ui.activity.DisplaySupportActivity;
import com.qiyei.appdemo.ui.activity.EasyJokeMainActivity;
import com.qiyei.appdemo.ui.activity.EncryptActivity;
import com.qiyei.appdemo.ui.activity.ImageSelectedTestActivity;
import com.qiyei.appdemo.ui.activity.NetworkTestActivity;
import com.qiyei.appdemo.ui.activity.OkioTestActivity;
import com.qiyei.appdemo.ui.activity.RecyclerViewTestActivity;
import com.qiyei.appdemo.ui.activity.RxjavaTestActivity;
import com.qiyei.appdemo.ui.activity.ScanCodeActivity;
import com.qiyei.appdemo.ui.activity.SkinTestActivity;
import com.qiyei.appdemo.ui.activity.TestActivity;
import com.qiyei.appdemo.ui.activity.ViewPagerTestActivity;
import com.qiyei.architecture.ui.activity.ArchitectureActivity;
import com.qiyei.componentdemo.ComponentDemoActivity;
import com.qiyei.cordova.EmbeddedWebViewActivity;
import com.qiyei.reactnative.ReactNativeDemoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2018/2/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MainMenuViewModel extends ViewModel{


    private List<MainMenu> mMenuList = new ArrayList<>();

    private String[] names = new String[]{"测试1","测试2 对话框测试","测试3 ViewPager测试","测试4 RecyclerViewTest","测试5 EasyJokeMain"
            ,"测试6 换肤测试","测试7 Banner测试","测试8 图片选择器测试","测试9 动态代理","测试10 数据中心"
            ,"测试11 测试异常信息","测试12 进程保活","测试13 Binder测试","测试14 网络框架测试","测试15 数据库框架测试"
            ,"测试16 加密测试","测试17 Android架构组件","测试18 Rxjava测试","测试19 Okio测试5","测试20 组件化测试"
            ,"测试21 Cordova测试","测试22 React Native测试","测试23 Dagger2测试","测试24 多屏幕分辨率适配","测试25 zxing二维码扫描"};

    private Class<?>[] clazzs = new Class[]{TestActivity.class,null,ViewPagerTestActivity.class,RecyclerViewTestActivity.class,EasyJokeMainActivity.class
            ,SkinTestActivity.class,BannerTestActivity.class,ImageSelectedTestActivity.class,null,DataCenterTestActivity.class
            ,null,null,BinderTestActivity.class,NetworkTestActivity.class,DatabaseTestActivity.class
            ,EncryptActivity.class,ArchitectureActivity.class,RxjavaTestActivity.class,OkioTestActivity.class
            ,ComponentDemoActivity.class,EmbeddedWebViewActivity.class,ReactNativeDemoActivity.class,DaggerDemoActivity.class, DisplaySupportActivity.class,ScanCodeActivity.class};


    private MutableLiveData<List<MainMenu>> mLiveData;


    public MainMenuViewModel() {
        for (int i = 0 ; i < names.length ; i++){
            MainMenu menu = new MainMenu(i+1,names[i],clazzs[i]);
            mMenuList.add(menu);
        }
        mLiveData = new MutableLiveData<>();
        mLiveData.setValue(mMenuList);
    }

    /**
     * 获取LiveData
     * @return
     */
    public MutableLiveData<List<MainMenu>> getMenuList(){
        return mLiveData;
    }

}
