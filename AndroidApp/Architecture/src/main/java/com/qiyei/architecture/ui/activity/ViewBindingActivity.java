package com.qiyei.architecture.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.qiyei.architecture.R;
import com.qiyei.architecture.databinding.ActivityViewBindingBinding;

public class ViewBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityViewBindingBinding binding = ActivityViewBindingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.tv.setText("这个时ViewBinding");
    }
}