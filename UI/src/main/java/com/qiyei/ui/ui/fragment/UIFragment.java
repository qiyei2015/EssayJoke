package com.qiyei.ui.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiyei.ui.R;
import com.qiyei.framework.fragment.BaseFragment;
import com.qiyei.sdk.ioc.ViewUtils;


/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class UIFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ui,container,false);
        ViewUtils.inject(this,view);
        return view;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
