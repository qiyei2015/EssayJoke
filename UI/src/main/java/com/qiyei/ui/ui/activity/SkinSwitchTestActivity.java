package com.qiyei.ui.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;


import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.framework.skin.SkinManager;
import com.qiyei.framework.skin.SkinResources;
import com.qiyei.framework.titlebar.CommonTitleBar;
import com.qiyei.sdk.util.ToastUtil;
import com.qiyei.ui.R;


import java.io.File;

public class SkinSwitchTestActivity extends BaseSkinActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_skin_switch_test);
    }

    @Override
    protected void initView() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        CommonTitleBar navigationBar = new
                CommonTitleBar.Builder(this)
                .setTitle("换肤测试")
                .build();

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn1) {
            skin(v);
        } else if (i == R.id.btn2) {
            skin1(v);
        } else if (i == R.id.btn3) {
            skin2(v);
        } else {

        }
    }

    @Override
    public void skinChange(SkinResources skinResources) {
        // 做一些第三方的改变
        ToastUtil.showLongToast("换肤了");
    }

    private void skin(View view){
        // 从服务器上下载

        String skinpath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator +"red.skin";
        // 换肤
        SkinManager.getInstance().loadSkin(skinpath);
    }

    private void skin1(View view){
        // 恢复默认
        SkinManager.getInstance().restore();
    }

    private void skin2(View view){
        // 跳转
        Intent intent = new Intent(this,SkinSwitchTestActivity.class);
        startActivity(intent);
    }
}
