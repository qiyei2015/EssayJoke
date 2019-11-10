package com.qiyei.performance.ui.fragment;


import android.view.View;

import com.qiyei.framework.common.model.MainMenu;
import com.qiyei.framework.ui.fragment.CommonListFragment;
import com.qiyei.performance.ui.activity.AppStartOptimizationActivity;


import java.util.ArrayList;
import java.util.List;


/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class PerformanceFragment extends CommonListFragment {


    private static final String TAG = "PerformanceFragment";
    /**
     * 菜单item
     */
    private List<MainMenu> mMenuList = new ArrayList<>();

    private String[] names = new String[]{"测试1 启动优化"};
    private Class<?>[] clazzs = new Class[]{AppStartOptimizationActivity.class};


    public PerformanceFragment() {
        super();
        for (int i = 0 ; i < names.length ; i++){
            MainMenu menu = new MainMenu(i+1,names[i],clazzs[i]);
            mMenuList.add(menu);
        }
    }

    @Override
    protected String getTAG() {
        return TAG;
    }


    @Override
    protected void initView(View view) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void initData() {
        setLiveData(mMenuList);
    }
}
