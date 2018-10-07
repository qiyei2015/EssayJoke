package com.qiyei.framework.extend

import android.content.Context
import android.widget.ImageView
import com.qiyei.sdk.image.ImageManager
import com.youth.banner.loader.ImageLoader

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class GlideImageLoader:ImageLoader() {

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        ImageManager.getInstance().loadImage(imageView,path as? String)
    }

}