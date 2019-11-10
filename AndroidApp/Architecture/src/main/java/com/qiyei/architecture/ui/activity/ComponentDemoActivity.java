package com.qiyei.architecture.ui.activity;

import android.os.Bundle;


import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.qiyei.architecture.R;


public class ComponentDemoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_demo);

        ARouter.getInstance().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
                ARouter.getInstance().build("/ModuleA/ModuleAActivity").navigation();
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
