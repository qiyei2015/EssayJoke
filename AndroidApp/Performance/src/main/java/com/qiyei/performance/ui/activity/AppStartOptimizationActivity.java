package com.qiyei.performance.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Process;
import android.widget.ImageView;

import com.qiyei.performance.R;

/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class AppStartOptimizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start_optimization);
        ImageView imageView = findViewById(R.id.imv);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.easyicon2);
        imageView.setImageBitmap(bitmap);
    }
}
