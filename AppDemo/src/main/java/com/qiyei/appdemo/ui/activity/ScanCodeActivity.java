package com.qiyei.appdemo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.qiyei.appdemo.R;
import com.qiyei.sdk.util.ToastUtil;

/**
 * @author Created by qiyei2015 on 2018/7/28.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ScanCodeActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        Button button1 = findViewById(R.id.btn1);

        button1.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, CaptureActivity.class),REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK){
                    ToastUtil.showShortToast("扫描返回成功");
                }
                break;
            default:
        }
    }
}
