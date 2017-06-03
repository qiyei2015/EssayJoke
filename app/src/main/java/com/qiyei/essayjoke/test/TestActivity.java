package com.qiyei.essayjoke.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.qiyei.essayjoke.activity.EasyJokeMainActivity;
import com.qiyei.essayjoke.model.User;
import com.qiyei.framework.db.DaoSupportFactory;
import com.qiyei.framework.db.IDaoSupport;
import com.qiyei.sdk.dialog.BaseDialog;
import com.qiyei.sdk.fixbug.FixDexManager;
import com.qiyei.sdk.ioc.OnClick;
import com.qiyei.sdk.ioc.ViewById;
import com.qiyei.sdk.util.ToastUtil;
import com.qiyei.essayjoke.R;
import com.qiyei.framework.activity.BaseSkinActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseSkinActivity {

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

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_test);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        btn1.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        fixBug();

        //如果没有权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ,Manifest.permission.ACCESS_COARSE_LOCATION },MY_PERMISSIONS_REQUEST_WRITE_STORE);
        }else {
            initDataBase();
        }
    }

    @Override
    public void onClick(View v) {
        ToastUtil.showLongToast(2/1 + "测试");
        startActivity(ColorTrackTextViewActivity.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORE){
            Log.d(TAG,"onRequestPermissionsResult,size:" + grantResults.length);

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED ){
                initDataBase();
            }else {
                ToastUtil.showLongToast("你拒绝了获取存储卡的权限");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick(R.id.btn2)
    private void testBtn2(View view){

        View view2 = LayoutInflater.from(this).inflate(R.layout.dialog_test,null);
        final BaseDialog dialog = new BaseDialog.Builder(this,R.layout.dialog_test)
                .setCancelable(true)
                //.setContentView(R.layout.dialog_test)
                //.setContentView(view2)
                .setText(R.id.dialog_content,"这是一个对话框，哈哈哈！")
                .setOnClickListener(R.id.dialog_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("对话框点击了确认");
                        //dialog.dismiss();
                    }
                })
                .setOnClickListener(R.id.dialog_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showLongToast("对话框点击了取消");
                        //dialog.dismiss();
                    }
                })
                .setFragmentManager(getSupportFragmentManager())
                .create();

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
    private void initDataBase(){
        IDaoSupport<User> userDao = DaoSupportFactory.getInstance().getDao(User.class);
        List<User> users = new ArrayList<>();
        for (int i = 0;i < 100;i++){
            users.add(new User("qiyei2015",i+1));
        }
        userDao.insert(users);
    }
}
