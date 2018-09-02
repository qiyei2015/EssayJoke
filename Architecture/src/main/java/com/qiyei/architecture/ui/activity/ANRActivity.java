package com.qiyei.architecture.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qiyei.architecture.R;
import com.qiyei.sdk.util.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 1、主线程对输入事件在5秒内没有处理完毕
 * 2、主线程在执行BroadcastReceiver的onReceive函数时10秒内没有执行完毕
 * 3、主线程在执行Service的各个生命周期函数时20秒内没有执行完毕
 */
public class ANRActivity extends AppCompatActivity {

    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;

    private static final String ACTION = "com.qiyei.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);

        initView();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        registerReceiver(new MyReceiver(),intentFilter);

    }


    void initView(){
        btn0 = findViewById(R.id.button0);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);


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

        /**
         * 模拟IO操作
         */
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (true){
                    writeFile();
                }
            }
        });

        /**
         * 广播超时
         */
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION);
                ANRActivity.this.sendBroadcast(intent);
            }
        });
    }


    private void writeFile(){
        File file = new File(getCacheDir().getAbsolutePath()+"/write.txt");

        try {
            if (!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            String text = "hello world";
            byte[] bytes = text.getBytes();
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION.equals(intent.getAction())){
                try {
                    Thread.sleep(30*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
