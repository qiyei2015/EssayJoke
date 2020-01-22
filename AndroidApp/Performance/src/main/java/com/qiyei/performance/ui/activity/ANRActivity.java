package com.qiyei.performance.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.qiyei.framework.util.Utils;
import com.qiyei.performance.R;
import com.qiyei.performance.ui.service.ANRService;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.ToastUtil;



/**
 * 1、主线程对输入事件在5秒内没有处理完毕
 * 2、主线程在执行BroadcastReceiver的onReceive函数时10秒内没有执行完毕
 * 3、主线程在执行Service的各个生命周期函数时20秒内没有执行完毕
 */
public class ANRActivity extends AppCompatActivity {

    private static final String TAG = "ANRActivity";

    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;

    private static final String ACTION1 = "com.qiyei.action1";

    private static final String ACTION2 = "com.qiyei.action2";

    Object mLock = new Object();


    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    LogManager.i(TAG,"receive message 1");
                    break;
                case 2:
                    LogManager.i(TAG,"receive message 2");
                    break;
                case 3:
                    LogManager.i(TAG,"receive message 3");
                    break;
                case 4:
                    LogManager.i(TAG,"receive message 4");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);

        initView();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION1);
        intentFilter.addAction(ACTION2);
        registerReceiver(new MyReceiver(),intentFilter);

    }


    void initView(){
        btn0 = findViewById(R.id.button0);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);

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
                Utils.writeFile();
            }
        });

        /**
         * 广播超时
         */
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ACTION1);
//                ANRActivity.this.sendBroadcast(intent);

                Intent intent1 = new Intent("com.qiyei.android.perfromance.ANRTEST");
                intent1.addFlags(0x01000000);
                ANRActivity.this.sendBroadcast(intent1);
                //writeXml()
            }
        });

        /**
         * service超时
         */
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ANRActivity.this, ANRService.class);
                startService(intent);
            }
        });

        /**
         * service超时
         */
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (mLock){
                            LogManager.i(TAG,Thread.currentThread().getName() + " get mLock in Thread");
                            while (true){
                                ;
                            }
                        }
                    }
                }).start();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessageDelayed(1,7 * 1000);
                mHandler.sendEmptyMessageDelayed(2,8 * 1000);
                mHandler.sendEmptyMessageDelayed(3,9 * 1000);
                mHandler.sendEmptyMessageDelayed(4,10 * 1000);
                LogManager.i(TAG,"wait for mLock in OnClickListener");
                synchronized (mLock){
                    LogManager.i(TAG,"get mLock in OnClickListener");
                }

//                Intent intent = new Intent(ACTION2);
//                ANRActivity.this.sendBroadcast(intent);
            }
        });

    }


    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION1.equals(intent.getAction())){
                Utils.writeFile();
            }else if (ACTION2.equals(intent.getAction())){
                LogManager.i(TAG,"wait for mLock in receiver");
                synchronized (mLock){
                    LogManager.i(TAG,"get mLock in receiver");
                }
            }
        }

    }

}
