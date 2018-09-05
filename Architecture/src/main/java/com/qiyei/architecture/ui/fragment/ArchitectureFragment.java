package com.qiyei.architecture.ui.fragment;



import android.view.View;

import com.qiyei.architecture.ui.activity.ANRActivity;
import com.qiyei.architecture.ui.activity.MemoryLeakActivity;
import com.qiyei.architecture.ui.activity.UIDrawActivity;
import com.qiyei.framework.common.model.MainMenu;
import com.qiyei.framework.fragment.CommonListFragment;

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

    private String[] names = new String[]{"测试1 UI绘制优化","测试2 ANR","测试3 内存泄漏"};
    private Class<?>[] clazzs = new Class[]{UIDrawActivity.class,ANRActivity.class, MemoryLeakActivity.class};


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
