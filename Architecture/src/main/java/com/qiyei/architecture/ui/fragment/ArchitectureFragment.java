package com.qiyei.architecture.ui.fragment;



import android.view.View;

import com.qiyei.architecture.ui.activity.ANRActivity;
import com.qiyei.architecture.ui.activity.ArchitectureActivity;
import com.qiyei.architecture.ui.activity.BinderTestActivity;
import com.qiyei.architecture.ui.activity.ComponentDemoActivity;
import com.qiyei.architecture.ui.activity.DataCenterTestActivity;
import com.qiyei.architecture.ui.activity.DatabaseTestActivity;
import com.qiyei.architecture.ui.activity.MemoryLeakActivity;
import com.qiyei.architecture.ui.activity.NetworkTestActivity;
import com.qiyei.architecture.ui.activity.ProcessKeepAliveActivity;
import com.qiyei.architecture.ui.activity.ProxyTestActivity;
import com.qiyei.architecture.ui.activity.UIDrawActivity;
import com.qiyei.framework.common.model.MainMenu;
import com.qiyei.framework.ui.fragment.CommonListFragment;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ArchitectureFragment extends CommonListFragment {

    /**
     * 菜单item
     */
    private List<MainMenu> mMenuList = new ArrayList<>();

    private String[] names = new String[]{"测试1 动态代理","测试2 数据中心","测试3 进程保活","测试4 Binder测试"
            ,"测试5 网络框架测试","测试6 数据库框架测试","测试7 Android架构组件","测试8 组件化测试"
            ,"测试9 UI绘制优化","测试10 ANR","测试11 内存泄漏"};
    private Class<?>[] clazzs = new Class[]{ProxyTestActivity.class,DataCenterTestActivity.class,ProcessKeepAliveActivity.class,BinderTestActivity.class
            ,NetworkTestActivity.class,DatabaseTestActivity.class,ArchitectureActivity.class,ComponentDemoActivity.class
            ,UIDrawActivity.class,ANRActivity.class, MemoryLeakActivity.class};


    public ArchitectureFragment() {
        super();
        for (int i = 0 ; i < names.length ; i++){
            MainMenu menu = new MainMenu(i+1,names[i],clazzs[i]);
            mMenuList.add(menu);
        }
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        setLiveData(mMenuList);
    }



}
