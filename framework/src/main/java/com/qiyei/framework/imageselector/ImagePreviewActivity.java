package com.qiyei.framework.imageselector;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qiyei.framework.R;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.sdk.log.LogUtil;

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
        LogUtil.d(TAG,"mImageList:" + mImageList.toString());
        Glide.with(this).load(mImageList.get(0)).override(1080,1500).into(mImageView);
    }

    @Override
    protected void initData() {
        mImageList = (List<String>) getIntent().getSerializableExtra(ImageSelector.KEY_RESULT);
    }

    @Override
    public void onClick(View v) {

    }
}
