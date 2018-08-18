package com.qiyei.android.media.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.qiyei.android.common.model.MainMenu;
import com.qiyei.android.media.activity.MediaRecordActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2018/8/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MediaViewModel extends ViewModel{

    /**
     * 菜单item
     */
    private List<MainMenu> mMenuList = new ArrayList<>();
    /**
     * LiveData
     */
    private MutableLiveData<List<MainMenu>> mLiveData;


    private String[] names = new String[]{"测试1 音视频的采集、编码、封包成 mp4 输出"};

    private Class<?>[] clazzs = new Class[]{MediaRecordActivity.class};


    public MediaViewModel() {
        for (int i = 0 ; i < names.length ; i++){
            MainMenu menu = new MainMenu(i+1,names[i],clazzs[i]);
            mMenuList.add(menu);
        }
        mLiveData = new MutableLiveData<>();
        mLiveData.setValue(mMenuList);

    }

    /**
     * @return {@link #mLiveData}
     */
    public MutableLiveData<List<MainMenu>> getLiveData() {
        return mLiveData;
    }

}
