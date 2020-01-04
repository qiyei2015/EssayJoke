package com.qiyei.ui.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.qiyei.ui.R;

public class ToolbarDemoActivity extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_demo);
        mToolbar = findViewById(R.id.toolbar);

        mToolbar.setTitle("Toolbar Demo");
        setSupportActionBar(mToolbar);

    }

}
