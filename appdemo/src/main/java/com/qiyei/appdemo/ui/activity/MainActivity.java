package com.qiyei.appdemo.ui.activity;

import android.Manifest;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;


import com.qiyei.appdemo.adapter.MainMenuAdapter;
import com.qiyei.appdemo.listener.MainMenuListener;
import com.qiyei.appdemo.model.MainMenu;
import com.qiyei.appdemo.service.RemoteService;
import com.qiyei.appdemo.service.TestService;
import com.qiyei.appdemo.viewmodel.MainMenuViewModel;
import com.qiyei.appdemo.viewmodel.NetworkLiveData;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.framework.titlebar.CommonTitleBar;
import com.qiyei.ioc.annotation.Bind;
import com.qiyei.sdk.db.DaoSupportFactory;
import com.qiyei.sdk.db.IDaoSupport;
import com.qiyei.sdk.dialog.BaseDialog;
import com.qiyei.sdk.dialog.DialogListener;
import com.qiyei.sdk.fixbug.FixDexManager;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.permission.PermissionFail;
import com.qiyei.sdk.permission.PermissionManager;
import com.qiyei.sdk.permission.PermissionSuccess;
import com.qiyei.sdk.util.AndroidUtil;
import com.qiyei.sdk.util.ToastUtil;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import com.qiyei.appdemo.R;
import com.qiyei.appdemo.model.Control;
import com.qiyei.appdemo.model.IControl;
import com.qiyei.appdemo.model.User;
import com.qiyei.sdk.view.xrecycler.base.CategoryItemDecoration;
import com.qiyei.sdk.xml.XmlManager;
import com.taobao.sophix.SophixManager;

/**
 * @author Created by qiyei2015 on 2017/8/28.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Bind(10)
public class MainActivity extends BaseSkinActivity {

    private RecyclerView mRecyclerView;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORE = 1;

    /**
     * ViewModel
     */
    private MainMenuViewModel mMenuViewModel;

    private MainMenuAdapter mMenuAdapter;

    /**
     * 标题栏
     */
    private CommonTitleBar mTitleBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PermissionManager.requestAllDangerousPermission(this);
        initData();
        initView();
        LogManager.i(TAG,"onCreate");
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mTitleBar = new CommonTitleBar.Builder(this)
                .setTitle("Demo测试")
                .setRightText("哈哈测试移动热修复")
                .build();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new CategoryItemDecoration(AndroidUtil.getDrawable(R.drawable.recyclerview_decoration)));

        mRecyclerView.setAdapter(mMenuAdapter);

        mMenuViewModel = ViewModelProviders.of(this).get(MainMenuViewModel.class);
        mMenuViewModel.getMenuList().observe(this, new Observer<List<MainMenu>>() {
            @Override
            public void onChanged(@Nullable List<MainMenu> mainMenus) {
                //update UI
                mMenuAdapter.setDatas(mainMenus);
            }
        });

        NetworkLiveData.getInstance(this).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer networkState) {
                //your code
            }
        });
    }

    @Override
    protected void initData() {
        //如果没有权限
        String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
                ,Manifest.permission.VIBRATE,Manifest.permission.CAMERA,Manifest.permission.SEND_SMS};

        PermissionManager.requestPermission(this,MY_PERMISSIONS_REQUEST_WRITE_STORE,permission);
//        fixBug();
        LogManager.i(TAG,"inner path:" + AndroidUtil.getInnerDataPath());
        LogManager.i(TAG,"external path:" + AndroidUtil.getExternalDataPath());
        LogManager.i(TAG,"sdcard path:" + AndroidUtil.getSdCardDataPath());

        mMenuAdapter = new MainMenuAdapter(this,null,R.layout.recyclerview_item);
        mMenuAdapter.setListener(new MyListener());
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this,requestCode,permissions,grantResults);

    }


    /**
     * 初始化数据库
     */
    @PermissionSuccess(requestCode = MY_PERMISSIONS_REQUEST_WRITE_STORE)
    private void initDataBase(){
        ToastUtil.showLongToast("申请权限成功");
        IDaoSupport<User> userDao = DaoSupportFactory.getInstance().getDao(User.class);
        List<User> users = new ArrayList<>();
        for (int i = 0;i < 100;i++){
            users.add(new User("qiyei2015",i+1));
        }
        userDao.insert(users);
    }

    @PermissionFail(requestCode = MY_PERMISSIONS_REQUEST_WRITE_STORE)
    private void initDataFail(){
        ToastUtil.showLongToast("申请权限失败");
    }

    private void dynamicProxy(){
        final Control control = new Control();

        IControl controlProxy = (IControl) Proxy.newProxyInstance(control.getClass().getClassLoader(), control.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                LogManager.d("invoke","first invoke method !");

                return method.invoke(control,args);
            }
        });
        controlProxy.printHello("hello world !");
    }

    /**
     * 跳转到菜单
     * @param menu
     */
    private void gotoMenuActivity(MainMenu menu){
        switch (menu.getId()){
            case 2:
                testBtn2(null);
                XmlManager.readConfigure();
                break;
            case 9:
                testBtn9(null);
                break;
            case 11:
                testBtn11(null);
                break;
            case 12:
                testBtn12(null);
                break;
            default:
                startActivity(menu.getClazz());
                break;
        }
    }

    private void testBtn2(View view){

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_test,null,false);

        BaseDialog dialog = new BaseDialog.Builder(this)
                .setCancelable(true)
                //.setContentView(R.layout.dialog_test)
                .setContentView(R.layout.dialog_test)
                .setText(R.id.dialog_content,"这是一个对话框，哈哈哈！")
                .setDialogListener(R.id.dialog_ok, new DialogListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("对话框点击了确认");

                    }
                })
                .setDialogListener(R.id.dialog_cancel, new DialogListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("对话框点击了取消");
                    }
                })
//                .setGravity(Gravity.BOTTOM)
//                .fullWidth()
                .setFragmentManager(getSupportFragmentManager())
                .build();

        dialog.show();
    }

    private void testBtn9(View view){
        dynamicProxy();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    private void testBtn11(View view){

        BaseDialog dialog = new BaseDialog.Builder(this)
                .setCancelable(true)
                .setContentView(R.layout.common_dialog)
                //.setContentView(contentView)
                .setText(R.id.id_dialog_title,"这是一个对话框标题！")
                .setText(R.id.id_tv_content,"对话框内容55555")
                .setText(R.id.id_tv_confirm,"确认")
                .setText(R.id.id_tv_cancel,"取消")
                .setDrawable(R.id.id_dialog_title_imv,R.drawable.icon_login_single)
                .setDialogListener(R.id.id_tv_confirm, new DialogListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("对话框点击了确认");

                    }
                })
                .setDialogListener(R.id.id_tv_cancel, new DialogListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("对话框点击了取消");
                    }
                })
//                .setGravity(Gravity.BOTTOM)
//                .fullWidth()
                .setFragmentManager(getSupportFragmentManager())
                .build();

        dialog.show();
    }

    private void testBtn12(View view){
        startService(new Intent(this,RemoteService.class));
        startService(new Intent(this, TestService.class));
    }

    private void fixBug(){
        File fixFile = new File(Environment.getExternalStorageDirectory(),"fix.dex");
        if (!fixFile.exists()){
            ToastUtil.showLongToast("fixFile is not exists !");
            return;
        }

        try {
            FixDexManager fixDexManager = new FixDexManager(this);
            fixDexManager.fixDex(fixFile.getAbsolutePath());
            ToastUtil.showLongToast("修复成功");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showLongToast("修复失败");
        }

    }

    private class MyListener implements MainMenuListener{
        @Override
        public void onClick(View v, MainMenu item, int position) {
            gotoMenuActivity(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogManager.i(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogManager.i(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogManager.i(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogManager.i(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        mMenuAdapter.removeListener();
        LogManager.i(TAG,"onDestroy");
        super.onDestroy();
    }

}
