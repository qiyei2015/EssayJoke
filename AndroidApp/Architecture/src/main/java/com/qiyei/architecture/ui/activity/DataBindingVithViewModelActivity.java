package com.qiyei.architecture.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.qiyei.architecture.R;
import com.qiyei.architecture.databinding.ActivityDataBindingVithViewModelBinding;
import com.qiyei.architecture.ui.viewmodel.DataBindingTestViewModel;

public class DataBindingVithViewModelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingVithViewModelBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_data_binding_vith_view_model);

        DataBindingTestViewModel viewModel = ViewModelProviders.of(this).get(DataBindingTestViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setName("hahah");
    }
}