package com.qiyei.essayjoke.activity;


import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qiyei.essayjoke.R;
import com.qiyei.essayjoke.fragment.FindFragment;
import com.qiyei.essayjoke.fragment.HomeFragment;
import com.qiyei.essayjoke.fragment.MessageFragment;
import com.qiyei.essayjoke.fragment.NewFragment;
import com.qiyei.essayjoke.test.TestActivity;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.framework.fragment.FragmentHelper;
import com.qiyei.framework.titlebar.CommonTitleBar;
import com.qiyei.sdk.ioc.ViewById;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/24.
 * Version: 1.0
 * Description: app的主界面
 */
public class HomeActivity extends BaseSkinActivity {

    @ViewById(R.id.main_tab_layout)
    private FrameLayout mMainTabLayout;
    @ViewById(R.id.tab_list)
    private RadioGroup mTabList;
    @ViewById(R.id.home_rb)
    private RadioButton mHomeButton;
    @ViewById(R.id.find_rb)
    private RadioButton mFindButton;
    @ViewById(R.id.new_rb)
    private RadioButton mNewButton;
    @ViewById(R.id.message_rb)
    private RadioButton mMessageButton;

    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private NewFragment mNewFragment;
    private MessageFragment mMessageFragment;

    private FragmentHelper mFragmentHelper;

    private CommonTitleBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void initData() {
        mContext = this;
        mFragmentHelper = new FragmentHelper(getSupportFragmentManager(),R.id.main_tab_layout);
    }

    @Override
    protected void initView() {
        navigationBar = new CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.home_page))
                .setRightText(getString(R.string.test))
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(TestActivity.class);
                    }
                })
                .build();
        //initSystemBar(R.color.title_bar_bg_day);
        //initViewPager();
        mHomeButton.setOnClickListener(this);
        mFindButton.setOnClickListener(this);
        mNewButton.setOnClickListener(this);
        mMessageButton.setOnClickListener(this);

        mHomeFragment = new HomeFragment();
        mFragmentHelper.add(mHomeFragment);
        mHomeButton.setChecked(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_rb:
                if (mHomeFragment == null){
                    mHomeFragment = new HomeFragment();
                }
                navigationBar.setTitle(getString(R.string.home_page));
                mFragmentHelper.switchFragment(mHomeFragment);
                break;
            case R.id.find_rb:
                if (mFindFragment == null){
                    mFindFragment = new FindFragment();
                }
                navigationBar.setTitle(getString(R.string.find));
                mFragmentHelper.switchFragment(mFindFragment);
                break;
            case R.id.new_rb:
                if (mNewFragment == null){
                    mNewFragment = new NewFragment();
                }
                navigationBar.setTitle(getString(R.string.fresh));
                mFragmentHelper.switchFragment(mNewFragment);
                break;
            case R.id.message_rb:
                if (mMessageFragment == null){
                    mMessageFragment = new MessageFragment();
                }
                navigationBar.setTitle(getString(R.string.message));
                mFragmentHelper.switchFragment(mMessageFragment);
                break;
            default:
                break;
        }
    }
}
