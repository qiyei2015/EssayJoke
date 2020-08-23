package com.qiyei.architecture.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.qiyei.architecture.R;
import com.qiyei.architecture.data.bean.DataBean;
import com.qiyei.architecture.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingBinding dataBindingBinding = DataBindingUtil.setContentView(this,R.layout.activity_data_binding);
        dataBindingBinding.setBean(new DataBean(3,"哈哈","嘿嘿"));
        dataBindingBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}