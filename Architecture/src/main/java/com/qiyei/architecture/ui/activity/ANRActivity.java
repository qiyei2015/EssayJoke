package com.qiyei.architecture.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qiyei.architecture.R;
import com.qiyei.sdk.util.ToastUtil;

/**
 * 1、主线程对输入事件在5秒内没有处理完毕
 * 2、主线程在执行BroadcastReceiver的onReceive函数时10秒内没有执行完毕
 * 3、主线程在执行Service的各个生命周期函数时20秒内没有执行完毕
 */
public class ANRActivity extends AppCompatActivity {

    Button btn0;
    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);

        initView();

    }


    void initView(){
        btn0 = findViewById(R.id.button0);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);

        btn0.setOnClickListener((view)->{
            ToastUtil.showLongToast("点击事件");
        });

        btn1.setOnClickListener((view)->{
            try {
                Thread.sleep(20*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (true){
                    ;
                }
            }
        });

    }


}
