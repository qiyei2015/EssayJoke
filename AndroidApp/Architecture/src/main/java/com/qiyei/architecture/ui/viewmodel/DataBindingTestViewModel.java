package com.qiyei.architecture.ui.viewmodel;



import androidx.lifecycle.ViewModel;



/**
 * @author Created by qiyei2015 on 2020/8/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: DataBindingTestViewModel
 */
public class DataBindingTestViewModel extends ViewModel {

    private String mId;

    private String name;

    /**
     * @return {@link #mId}
     */
    public String getId() {
        return mId;
    }

    /**
     * @param mId the {@link #mId} to set
     */
    public void setId(String id) {
        mId = id;
    }

    /**
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the {@link #name} to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
