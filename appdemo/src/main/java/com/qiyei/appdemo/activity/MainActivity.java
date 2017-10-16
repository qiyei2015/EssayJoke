package com.qiyei.appdemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


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
import com.taobao.sophix.SophixManager;

public class MainActivity extends BaseSkinActivity {

    @ViewById(R.id.btn1)
    private Button btn1;
    @ViewById(R.id.btn2)
    private Button btn2;
    @ViewById(R.id.btn3)
    private Button btn3;
    @ViewById(R.id.btn4)
    private Button btn4;
    @ViewById(R.id.btn5)
    private Button btn5;
    @ViewById(R.id.btn6)
    private Button btn6;

    @ViewById(R.id.btn7)
    private Button btn7;
    @ViewById(R.id.btn8)
    private Button btn8;
    @ViewById(R.id.btn9)
    private Button btn9;

    @ViewById(R.id.btn10)
    private Button btn10;

    @ViewById(R.id.btn11)
    private Button btn11;

    @ViewById(R.id.btn12)
    private Button btn12;

    @ViewById(R.id.btn13)
    private Button btn13;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORE = 1;

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
                .setRightText("哈哈")
                .build();
        btn1.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        //如果没有权限

        String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
                ,Manifest.permission.VIBRATE,Manifest.permission.CAMERA,Manifest.permission.SEND_SMS};


        PermissionManager.requestPermission(this,MY_PERMISSIONS_REQUEST_WRITE_STORE,permission);

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.VIBRATE},MY_PERMISSIONS_REQUEST_WRITE_STORE);
//        }else {
//            initDataBase();
//
//        }
//        fixBug();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1){
            ToastUtil.showLongToast(2/1 + "测试");
            startActivity(TestActivity.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORE){
//            LogManager.d(TAG,"onRequestPermissionsResult,size:" + grantResults.length);
//
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
//                initDataBase();
//            }else {
//                ToastUtil.showLongToast("你拒绝了获取存储卡的权限");
//            }
//            return;
//        }
        PermissionManager.onRequestPermissionsResult(this,requestCode,permissions,grantResults);

    }

    @OnClick(R.id.btn2)
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

    @OnClick(R.id.btn3)
    private void testBtn3(View view){
        startActivity(ViewPagerTestActivity.class);
    }

    @OnClick(R.id.btn4)
    private void testBtn4(View view){
        startActivity(RecyclerViewTestActivity.class);
    }

    @OnClick(R.id.btn5)
    private void testBtn5(View view){
        startActivity(EasyJokeMainActivity.class);
    }

    @OnClick(R.id.btn6)
    private void testBtn6(View view){
        startActivity(SkinTestActivity.class);
    }

    @OnClick(R.id.btn7)
    private void testBtn7(View view){
        startActivity(BannerTestActivity.class);
    }

    @OnClick(R.id.btn8)
    private void testBtn8(View view){
        startActivity(ImageSelectedTestActivity.class);
    }

    @OnClick(R.id.btn9)
    private void testBtn9(View view){
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
        dynamicProxy();
    }

    @OnClick(R.id.btn10)
    private void testBtn10(View view){
        startActivity(DataCenterTestActivity.class);
    }

    @OnClick(R.id.btn11)
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

    @OnClick(R.id.btn12)
    private void testBtn12(View view){
        startService(new Intent(this,RemoteService.class));
        startService(new Intent(this, TestService.class));
    }

    @OnClick(R.id.btn13)
    private void testBtn13(View view){
        startActivity(BinderTestActivity.class);
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

}
