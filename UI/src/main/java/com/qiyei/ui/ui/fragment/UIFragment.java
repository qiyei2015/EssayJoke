package com.qiyei.ui.ui.fragment;


import android.view.View;

import com.qiyei.framework.common.model.MainMenu;

import com.qiyei.framework.ui.fragment.CommonListFragment;
import com.qiyei.ui.ui.activity.BannerTestActivity;
import com.qiyei.ui.ui.activity.CommonDialogActivity;
import com.qiyei.ui.ui.activity.EssayJokeMainActivity;
import com.qiyei.ui.ui.activity.ImageChooseTestActivity;
import com.qiyei.ui.ui.activity.MultiDisplaySupportActivity;
import com.qiyei.ui.ui.activity.RecyclerViewTestActivity;
import com.qiyei.ui.ui.activity.ScanCodeActivity;
import com.qiyei.ui.ui.activity.SkinSwitchTestActivity;
import com.qiyei.ui.ui.activity.ViewPagerTestActivity;
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

    private String[] names = new String[]{"测试1 XGridView","测试2 自定义对话框测试","测试3 ViewPager测试"
            ,"测试4 RecyclerViewTest","测试5 EssayJokeMain","测试6 换肤测试","测试7 Banner测试","测试8 图片选择器测试"
            ,"测试9 多屏幕分辨率适配","测试 二维码扫描"};
    private Class<?>[] clazzs = new Class[]{XGrirdViewActivity.class,CommonDialogActivity.class,ViewPagerTestActivity.class
            ,RecyclerViewTestActivity.class,EssayJokeMainActivity.class,SkinSwitchTestActivity.class,BannerTestActivity.class,ImageChooseTestActivity.class
            ,MultiDisplaySupportActivity.class,ScanCodeActivity.class};

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
