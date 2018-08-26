package com.qiyei.media.ui.fragment;




import android.view.View;

import com.qiyei.framework.common.model.MainMenu;
import com.qiyei.framework.fragment.CommonListFragment;
import com.qiyei.media.ui.activity.MediaRecordActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Created by qiyei2015 on 2018/8/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MediaFragment extends CommonListFragment{

    /**
     * 菜单item
     */
    private List<MainMenu> mMenuList = new ArrayList<>();

    private String[] names = new String[]{"测试1 音视频的采集、编码、封包成 mp4 输出"};
    private Class<?>[] clazzs = new Class[]{MediaRecordActivity.class};


    public MediaFragment() {
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
