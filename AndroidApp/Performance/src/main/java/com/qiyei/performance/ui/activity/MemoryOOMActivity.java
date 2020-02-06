package com.qiyei.performance.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qiyei.performance.R;

import java.util.ArrayList;
import java.util.List;

public class MemoryOOMActivity extends AppCompatActivity {

    private static final String TAG = "MemoryOOMActivity";

    //10M
    private static final int SIZE = 10* 1024*1024;

    private static List sList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_oom);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0 ;i < 1000;i++){
                        byte[] bytes = new byte[SIZE];
                        sList.add(bytes);
                        Log.i(TAG,"new array " + i);
                    }
                } catch (OutOfMemoryError error){
                    Log.e(TAG,"error:" + error.getMessage());
                }
            }
        });
    }
}
