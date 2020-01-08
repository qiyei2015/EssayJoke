package com.qiyei.android.home.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

import com.qiyei.android.R;
import com.qiyei.architecture.ui.fragment.ArchitectureFragment;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.framework.ui.fragment.FragmentHelper;
import com.qiyei.media.ui.fragment.MediaFragment;
import com.qiyei.ndk.ui.fragment.NDKFragment;
import com.qiyei.opensource.ui.fragment.OpenSourceFragment;
import com.qiyei.performance.ui.fragment.PerformanceFragment;
import com.qiyei.sdk.ioc.ViewById;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.ui.ui.fragment.UIFragment;

/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class HomeActivity extends BaseSkinActivity {

    @ViewById(R.id.ui_rb)
    private RadioButton mUIButton;
    @ViewById(R.id.open_source_rb)
    private RadioButton mOpenSourceButton;
    @ViewById(R.id.architecture_rb)
    private RadioButton mArchitectureButton;
    @ViewById(R.id.performance_rb)
    private RadioButton mPerformanceButton;
    @ViewById(R.id.media_rb)
    private RadioButton mMediaButton;
    @ViewById(R.id.ndk_rb)
    private RadioButton mNDKButton;

    private FragmentHelper mFragmentHelper;

    private UIFragment mUIFragment;
    private OpenSourceFragment mOpenSourceFragment;
    private NDKFragment mNDKFragment;
    private ArchitectureFragment mArchitectureFragment;
    private PerformanceFragment mPerformanceFragment;
    private MediaFragment mMediaFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        LogManager.i("HomeActivity2","onCreate");
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void initData() {
        mFragmentHelper = new FragmentHelper(getSupportFragmentManager(),R.id.main_tab_layout);
    }

    @Override
    protected void initView() {
        mUIButton.setOnClickListener(this);
        mOpenSourceButton.setOnClickListener(this);
        mArchitectureButton.setOnClickListener(this);
        mPerformanceButton.setOnClickListener(this);
        mMediaButton.setOnClickListener(this);
        mNDKButton.setOnClickListener(this);

        mUIFragment = new UIFragment();
        mFragmentHelper.add(mUIFragment);
        mUIButton.setChecked(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ui_rb:
                if (mUIFragment == null){
                    mUIFragment = new UIFragment();
                }
                mFragmentHelper.switchFragment(mUIFragment);
                break;
            case R.id.open_source_rb:
                if (mOpenSourceFragment == null){
                    mOpenSourceFragment = new OpenSourceFragment();
                }
                mFragmentHelper.switchFragment(mOpenSourceFragment);
                break;
            case R.id.architecture_rb:
                if (mArchitectureFragment == null){
                    mArchitectureFragment = new ArchitectureFragment();
                }
                mFragmentHelper.switchFragment(mArchitectureFragment);
                break;
            case R.id.performance_rb:
                if (mPerformanceFragment == null){
                    mPerformanceFragment = new PerformanceFragment();
                }
                mFragmentHelper.switchFragment(mPerformanceFragment);
                break;
            case R.id.media_rb:
                if (mMediaFragment == null){
                    mMediaFragment = new MediaFragment();
                }
                mFragmentHelper.switchFragment(mMediaFragment);
                break;
            case R.id.ndk_rb:
                if (mNDKFragment == null){
                    mNDKFragment = new NDKFragment();
                }
                mFragmentHelper.switchFragment(mNDKFragment);
                break;
            default:
                break;
        }
    }

}
