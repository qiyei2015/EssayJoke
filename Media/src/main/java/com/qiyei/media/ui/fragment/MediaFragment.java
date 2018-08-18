package com.qiyei.media.ui.fragment;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.qiyei.media.viewmodel.MediaViewModel;
import com.qiyei.framework.fragment.BaseFragment;
import com.qiyei.framework.common.adapter.MainMenuAdapter;
import com.qiyei.framework.common.listener.MainMenuListener;
import com.qiyei.framework.common.model.MainMenu;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.AndroidUtil;
import com.qiyei.sdk.view.xrecycler.base.CategoryItemDecoration;

import com.qiyei.media.R;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2018/8/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MediaFragment extends BaseFragment {


    private RecyclerView mRecyclerView;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORE = 1;

    /**
     * ViewModel
     */
    private MediaViewModel mMenuViewModel;

    private MainMenuAdapter mMenuAdapter;


    public MediaFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogManager.i(getTAG(),"onCreateView");
        View contentView = inflater.inflate(R.layout.fragment_media, container, false);
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
        return "MediaFragment";
    }


    private class MyListener implements MainMenuListener {
        @Override
        public void onClick(View v, MainMenu item, int position) {
            gotoMenuActivity(item);
        }
    }

    private void initView(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new CategoryItemDecoration(AndroidUtil.getDrawable(R.drawable.recyclerview_decoration)));

    }

    private void initData(){
        mMenuAdapter = new MainMenuAdapter(getContext(),null,R.layout.recyclerview_main_menu_item);
        mMenuAdapter.setListener(new MyListener());

        mMenuViewModel = ViewModelProviders.of(this).get(MediaViewModel.class);
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
     * 跳转到菜单
     * @param menu
     */
    private void gotoMenuActivity(MainMenu menu){
        switch (menu.getId()){
            default:
                startActivity(menu.getClazz());
                break;
        }
    }


}
