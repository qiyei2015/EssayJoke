package com.qiyei.ui.ui.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import com.qiyei.sdk.display.DisplayManager;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.ui.R;

public class MultiDisplaySupportActivity extends AppCompatActivity {

    private static final String TAG = "DisplaySupportActivity";

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_display_support);

        mImageView1 = (ImageView) findViewById(R.id.image_view1);
        mImageView2 = (ImageView) findViewById(R.id.image_view2);
        mImageView3 = (ImageView) findViewById(R.id.image_view3);

        DisplayManager.getDisplayInfo();
        getImageInfo();


    }

    private void getImageInfo() {
        mImageView1.post(new Runnable() {
            @Override
            public void run() {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView1.getDrawable();
                if (null != bitmapDrawable) {
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int byteCount = bitmap.getByteCount();
                    LogManager.i(TAG,"drawable ----> width=" + width + ",height=" + height);
                    LogManager.i(TAG,"drawable ----> byteCount=" + byteCount);
                }
            }
        });

        mImageView2.post(new Runnable() {
            @Override
            public void run() {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView2.getDrawable();
                if (null != bitmapDrawable) {
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int byteCount = bitmap.getByteCount();
                    LogManager.i(TAG,"mipmap-xxhdpi ----> width=" + width + ",height=" + height);
                    LogManager.i(TAG,"mipmap-xxhdpi ----> byteCount=" + byteCount);
                }
            }
        });

        mImageView3.post(new Runnable() {
            @Override
            public void run() {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView3.getDrawable();
                if (null != bitmapDrawable) {
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int byteCount = bitmap.getByteCount();
                    LogManager.i(TAG,"drawable-no ----> width=" + width + ",height=" + height);
                    LogManager.i(TAG,"drawable-no ----> byteCount=" + byteCount);
                }
            }
        });

    }
}
