package com.qiyei.framework.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiyei.framework.R;
import com.qiyei.framework.common.adapter.MainMenuAdapter;
import com.qiyei.framework.common.listener.MainMenuListener;
import com.qiyei.framework.common.model.MainMenu;
import com.qiyei.framework.viewmodel.CommonListViewModel;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.AndroidUtil;
import com.qiyei.sdk.view.xrecycler.base.CategoryItemDecoration;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2018/8/26.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 公共的列表Fragment
 */
public abstract class CommonListFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

    /**
     * ViewModel
     */
    private CommonListViewModel mMenuViewModel;

    private MainMenuAdapter mMenuAdapter;

    public CommonListFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogManager.i(getTAG(),"onCreateView");
        View contentView = inflater.inflate(R.layout.fragment_common_list, container, false);
        init(contentView);
        initView(contentView);
        initData();
        return contentView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected String getTAG() {
        return "CommonListFragment";
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * View初始化
     * @param view
     */
    protected abstract void initView(View view);

    /**
     * 数据绑定及初始化
     */
    protected abstract void initData();

    /**
     * 初始化
     * @param view
     */
    private void init(View view){
        //初始化RV
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new CategoryItemDecoration(AndroidUtil.getDrawable(R.drawable.recyclerview_decoration)));
        //初始化Adapter
        mMenuAdapter = new MainMenuAdapter(getContext(),null,R.layout.recyclerview_main_menu_item);
        mMenuAdapter.setListener(new MyListener());
        //初始化ViewModel
        mMenuViewModel = ViewModelProviders.of(this).get(CommonListViewModel.class);
        mMenuViewModel.getLiveData().observe(this, new Observer<List<MainMenu>>() {
            @Override
            public void onChanged(@Nullable List<MainMenu> mainMenus) {
                //update UI
                mMenuAdapter.setDatas(mainMenus);
            }
        });

        mRecyclerView.setAdapter(mMenuAdapter);
    }

    /**
     * 设置显示的数据
     */
    protected void setLiveData(List<MainMenu> list){
        //主动更新数据
        mMenuViewModel.getLiveData().setValue(list);
    }


    /**
     * 跳转到菜单
     * @param menu
     */
    protected void gotoMenuActivity(MainMenu menu){
        switch (menu.getId()){
            default:
                startActivity(menu.getClazz());
                break;
        }
    }

    private class MyListener implements MainMenuListener {

        @Override
        public void click(View v, MainMenu item, int position) {
            gotoMenuActivity(item);
        }
    }

}
