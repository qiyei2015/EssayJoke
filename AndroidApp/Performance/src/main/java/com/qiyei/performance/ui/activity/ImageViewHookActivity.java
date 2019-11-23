package com.qiyei.performance.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.qiyei.performance.R;

/**
 * @author Created by qiyei2015 on 2019/11/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ImageViewHookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_hook);
        ImageView imageView = findViewById(R.id.imv);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.easyicon2);
        imageView.setImageBitmap(bitmap);
    }
}
