package com.qiyei.performance.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.qiyei.performance.R;

public class MemoryOptimizationActivity extends AppCompatActivity {

    private static final int MSG_MEMORY_JITTER = 1;
    private static final int TIME_OUT = 50;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_MEMORY_JITTER:
                    for (int i = 0;i < 500;i++){
                        Bitmap bitmap = Bitmap.createBitmap(1920,1080, Bitmap.Config.ARGB_8888);
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
