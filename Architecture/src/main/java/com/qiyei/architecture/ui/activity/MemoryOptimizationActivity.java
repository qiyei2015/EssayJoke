package com.qiyei.architecture.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qiyei.architecture.R;

public class MemoryOptimizationActivity extends AppCompatActivity {

    private static final int MSG_MEMORY_JITTER = 1;
    private static final int TIME_OUT = 50;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_MEMORY_JITTER:
                    for (int i = 0;i < 5000;i++){
                        String[] message1 = new String[10000];
                    }
                    mHandler.sendEmptyMessageDelayed(MSG_MEMORY_JITTER,20);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_optimization);

        findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(MSG_MEMORY_JITTER);
            }
        });
    }
}
