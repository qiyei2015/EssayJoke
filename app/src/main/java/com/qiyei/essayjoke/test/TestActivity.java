package com.qiyei.essayjoke.test;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.qiyei.baselibrary.dialog.BaseDialog;
import com.qiyei.baselibrary.fixbug.FixDexManager;
import com.qiyei.baselibrary.ioc.OnClick;
import com.qiyei.baselibrary.ioc.ViewById;
import com.qiyei.baselibrary.util.ToastUtil;
import com.qiyei.essayjoke.R;
import com.qiyei.framework.activity.BaseSkinActivity;

import java.io.File;

public class TestActivity extends BaseSkinActivity {

    @ViewById(R.id.btn1)
    private Button btn1;
    @ViewById(R.id.btn2)
    private Button btn2;
    @ViewById(R.id.btn3)
    private Button btn3;
    @ViewById(R.id.btn4)
    private Button btn4;

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
    }

    @Override
    public void onClick(View v) {
        ToastUtil.showLongToast(2/1 + "测试");
        startActivity(ColorTrackTextViewActivity.class);
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
