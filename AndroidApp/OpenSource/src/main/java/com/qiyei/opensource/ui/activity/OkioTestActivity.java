package com.qiyei.opensource.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.qiyei.opensource.R;
import com.qiyei.sdk.log.LogManager;

import java.io.File;
import java.io.IOException;

import okio.BufferedSource;
import okio.Okio;

public class OkioTestActivity extends AppCompatActivity {

    private static final String TAG = "OkioTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okio_test);
        testOkio();
    }

    private void testOkio(){

        File file = new File("RxjavaTestActivity.java");

        //读测试
        try {
            BufferedSource bufferedSource = Okio.buffer(Okio.source(file));
            String s = bufferedSource.readUtf8();
            LogManager.i(TAG,"BufferedSource read:" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
