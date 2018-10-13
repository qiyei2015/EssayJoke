package com.qiyei.ui.ui.fragment;


import android.view.View;

import com.qiyei.framework.common.model.MainMenu;

import com.qiyei.framework.ui.fragment.CommonListFragment;
import com.qiyei.ui.ui.activity.ScanCodeActivity;
import com.qiyei.ui.ui.activity.XGrirdViewActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class UIFragment extends CommonListFragment {


    /**
     * 菜单item
     */
    private List<MainMenu> mMenuList = new ArrayList<>();

    private String[] names = new String[]{"测试1 XGridView","测试2 二维码扫描"};
    private Class<?>[] clazzs = new Class[]{XGrirdViewActivity.class,ScanCodeActivity.class};


    public UIFragment() {
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


    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
