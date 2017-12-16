package com.qiyei.appdemo.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import com.qiyei.appdemo.model.MainMenu;
import com.qiyei.appdemo.service.RemoteService;
import com.qiyei.appdemo.service.TestService;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.framework.titlebar.CommonTitleBar;
import com.qiyei.sdk.db.DaoSupportFactory;
import com.qiyei.sdk.db.IDaoSupport;
import com.qiyei.sdk.dialog.BaseDialog;
import com.qiyei.sdk.dialog.DialogListener;
import com.qiyei.sdk.fixbug.FixDexManager;
import com.qiyei.sdk.ioc.OnClick;
import com.qiyei.sdk.ioc.ViewById;
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
import com.qiyei.sdk.view.xrecycler.XRecyclerAdapter;
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter;
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder;
import com.qiyei.sdk.view.xrecycler.base.CategoryItemDecoration;
import com.taobao.sophix.SophixManager;

/**
 * @author Created by qiyei2015 on 2017/8/28.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MainActivity extends BaseSkinActivity {
    private RecyclerView mRecyclerView;
    private List<MainMenu> mMenuList = new ArrayList<>();

    private String[] names = new String[]{"测试1","测试2 对话框测试","测试3 ViewPager测试","测试4 RecyclerViewTest","测试5 EasyJokeMain"
            ,"测试6 换肤测试","测试7 Banner测试","测试8 图片选择器测试","测试9 动态代理","测试10 数据中心"
            ,"测试11 测试异常信息","测试12 进程保活","测试13 Binder测试","测试14 网络框架测试","测试15 数据库框架测试"
            ,"测试16 加密测试"};

    private Class<?>[] clazzs = new Class[]{TestActivity.class,null,ViewPagerTestActivity.class,RecyclerViewTestActivity.class,EasyJokeMainActivity.class
            ,SkinTestActivity.class,BannerTestActivity.class,ImageSelectedTestActivity.class,null,DataCenterTestActivity.class
            ,null,null,BinderTestActivity.class,NetworkTestActivity.class,DatabaseTestActivity.class
            ,EncryptActivity.class};

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORE = 1;

    /**
     * adapter
     */
    private class MenuAdapter extends BaseRecyclerAdapter<MainMenu>{

        public MenuAdapter() {
        }

        public MenuAdapter(Context context, List<MainMenu> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, MainMenu item, int position) {
            holder.setText(R.id.tv,item.getName());
            holder.setOnClickListener(R.id.tv, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoMenuActivity(item);
                }
            });
        }
    }

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
        mRecyclerView.addItemDecoration(new CategoryItemDecoration(getDrawable(R.drawable.recyclerview_decoration)));
        mRecyclerView.setAdapter(new MenuAdapter(this,mMenuList,R.layout.recyclerview_item));
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

        for (int i = 0 ; i < names.length ; i++){
            MainMenu menu = new MainMenu(i+1,names[i],clazzs[i]);
            mMenuList.add(menu);
        }
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
                .setText(R.id.id_tv_content,"对话框内容")
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


}
