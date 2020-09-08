package com.qiyei.architecture.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.EditText;

import com.qiyei.architecture.R;
import com.qiyei.architecture.databinding.ActivityDataBindingWithViewModelBinding;
import com.qiyei.architecture.ui.viewmodel.DataBindingTestViewModel;

public class DataBindingWithViewModelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingWithViewModelBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_data_binding_with_view_model);

        DataBindingTestViewModel viewModel = ViewModelProviders.of(this).get(DataBindingTestViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setName("hahah");
    }
}