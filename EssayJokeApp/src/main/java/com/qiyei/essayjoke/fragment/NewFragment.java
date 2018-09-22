package com.qiyei.essayjoke.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiyei.essayjoke.R;

import com.qiyei.framework.ui.fragment.BaseFragment;
import com.qiyei.sdk.ioc.ViewUtils;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/24.
 * Version: 1.0
 * Description:
 */
public class NewFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new,container,false);
        ViewUtils.inject(this,view);

        return view;
    }

}
