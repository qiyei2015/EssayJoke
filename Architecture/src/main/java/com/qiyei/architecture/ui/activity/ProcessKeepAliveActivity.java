package com.qiyei.architecture.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qiyei.architecture.R;
import com.qiyei.architecture.ui.service.RemoteService;
import com.qiyei.architecture.ui.service.TestService;


public class ProcessKeepAliveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_keep_alive);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startKeepAliveService(v);
            }
        });
    }

    private void startKeepAliveService(View view){
        startService(new Intent(this,RemoteService.class));
        startService(new Intent(this, TestService.class));
    }
}
