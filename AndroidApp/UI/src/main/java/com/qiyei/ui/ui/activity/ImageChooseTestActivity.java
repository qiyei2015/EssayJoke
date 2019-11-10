package com.qiyei.ui.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import com.qiyei.framework.imageselector.ImageSelector;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.ImageUtil;
import com.qiyei.ui.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageChooseTestActivity extends AppCompatActivity {

    private static final String TAG = "ImageSelected";

    private Button mButton;
    private ImageView mImageView;

    private static final int requestCode = 2;

    private List<String> mImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_choose_test);

        mButton = (Button) findViewById(R.id.btn1);
        mImageView = (ImageView) findViewById(R.id.imv);
        mImageList = new ArrayList<>();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector.create().multi().count(3).showCamera(true).start(ImageChooseTestActivity.this,requestCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == requestCode){
                mImageList = (List<String>) data.getSerializableExtra(ImageSelector.KEY_RESULT);
                LogManager.d(TAG,"image -->"+mImageList.toString());
                String path = mImageList.get(0);

                //Bitmap bitmap = ImageUtil.scaleImage(BitmapFactory.decodeFile(path),800,480);
                Bitmap bitmap = ImageUtil.cropImage(BitmapFactory.decodeFile(path),1000,500,ImageUtil.TOP_LEFT);

                ImageUtil.compressImage(bitmap,50, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                        new File(path).getName());
                Bitmap bitmap2 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                        new File(path).getName());
                mImageView.setImageBitmap(bitmap2);

                //Glide.with(this).load(mImageList.get(0)).override(1080,1500).into(mImageView);
            }
        }

    }
}
