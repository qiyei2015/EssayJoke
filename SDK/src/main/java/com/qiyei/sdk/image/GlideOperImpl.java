package com.qiyei.sdk.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/20.
 * Version: 1.0
 * Description: 采用Glide操作的Image图片管理实现
 */
public class GlideOperImpl implements IImageOper {

    @Override
    public void init() {

    }

    @Override
    public void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @Override
    public void loadImage(ImageView imageView, String url, int placeResId, int errResId) {
        Glide.with(imageView.getContext()).load(url).placeholder(placeResId).error(errResId).into(imageView);
    }

    @Override
    public void loadImage(ImageView imageView, File file) {
        //Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @Override
    public void loadImage(ImageView imageView, int resId) {
        //Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @Override
    public void loadImage(ImageView imageView, int targetX, int targetY, String url) {
        Glide.with(imageView.getContext()).load(url).override(targetX,targetY).into(imageView);
    }

}
