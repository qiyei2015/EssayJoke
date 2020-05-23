package com.qiyei.opensource.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.qiyei.opensource.R;
import com.tencent.mmkv.MMKV;

public class MMKVDemoActivity extends AppCompatActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmkv_demo);
        mTextView = findViewById(R.id.tv1);
        MMKV mmkv = MMKV.defaultMMKV();
        mTextView.setText(mmkv.decodeString("key1"));
        mmkv.encode("key1","hello mmkv");
    }
}
