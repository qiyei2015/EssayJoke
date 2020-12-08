package com.qiyei.architecture.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentView();
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class<?> clazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            try {
                Method method = clazz.getDeclaredMethod("inflate", LayoutInflater.class);
                mBinding = (VB) method.invoke(null, getLayoutInflater());
                setContentView(mBinding.getRoot());
            } catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onBeforeSetContentView(){

    }


}
