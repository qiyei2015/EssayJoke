package com.qiyei.framework.imageselector;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.qiyei.framework.R;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.sdk.image.ImageManager;
import com.qiyei.sdk.log.LogManager;

import java.util.List;

public class ImagePreviewActivity extends BaseSkinActivity {

    private ImageView mImageView;

    /**
     * 选择的图片结果
     */
    private List<String> mImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initData();
        initView();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_image_preview);
    }

    @Override
    protected void initView() {
        mImageView = (ImageView) findViewById(R.id.image_view);
        LogManager.d(TAG,"mImageList:" + mImageList.toString());
        ImageManager.getInstance().loadImage(mImageView,1080,1500,mImageList.get(0));
    }

    @Override
    protected void initData() {
        mImageList = (List<String>) getIntent().getSerializableExtra(ImageSelector.KEY_RESULT);
    }

    @Override
    public void onClick(View v) {

    }
}
