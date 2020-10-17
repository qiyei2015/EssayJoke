package com.qiyei.architecture.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.qiyei.architecture.R;
import com.qiyei.architecture.data.bean.DataBean;
import com.qiyei.architecture.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {

    DataBean mDataBean;
    int i = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingBinding dataBindingBinding = DataBindingUtil.setContentView(this,R.layout.activity_data_binding);

        mDataBean = new DataBean(3,"哈哈","嘿嘿");

        dataBindingBinding.setBean(mDataBean);
        dataBindingBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DataBindingWithViewModelActivity.class));
            }
        });

        dataBindingBinding.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("DataBindingActivity","data.value=" + mDataBean.getValue());
                i++;
                mDataBean.setName("哈哈 " + i);
            }
        });
    }
}