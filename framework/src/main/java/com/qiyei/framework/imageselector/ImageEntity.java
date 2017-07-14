package com.qiyei.framework.imageselector;

import android.text.TextUtils;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/8.
 * Version: 1.0
 * Description: 图片选择器的图片对象
 */
public class ImageEntity {

    public String path;
    public String name;
    public long time;

    public ImageEntity(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ImageEntity) {
            ImageEntity compare = (ImageEntity) o;
            return TextUtils.equals(this.path, compare.path);
        }
        return false;
    }
}
