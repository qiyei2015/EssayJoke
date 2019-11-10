package com.qiyei.framework.imageselector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * Created by Darren on 2016/12/21.
 * Email: 240336124@qq.com
 * Description: 图片选择的链式调用
 */

public class ImageSelector {
    /**
     * 调试用的TAG
     */
    public static final String TAG = ImageSelector.class.getSimpleName();
    /**
     * 图片选择的请求码
     */
    public static final int IMAGE_SELECT = 1;
    /**
     * 相机请求码
     */
    public final static int REQUEST_CAMERA = 0x0045;

    /**
     * 多选
     */
    public static final int MODE_MULTI = 1;
    /**
     * 单选
     */
    public static final int MODE_SINGLE = 2;

    /**
     * 参数
     */
    public static final String KEY_ARGS = "key_args";
    /**
     * 选择图片模式
     */
    public static final String KEY_SELECT_MODE = "key_select_mode";
    /**
     * 是否显示相机的EXTRA_KEY
     */
    public static final String KEY_SHOW_CAMERA = "SHOW_CAMERA";
    /**
     * 总共可以选择多少张图片的EXTRA_KEY
     */
    public static final String KEY_MAX_COUNT = "key_max_count";
    /**
     * 原始的图片路径的EXTRA_KEY
     */
    public static final String KEY_SELECT_LIST = "key_select_list";
    /**
     * 返回选择图片列表的EXTRA_KEY
     */
    public static final String KEY_RESULT = "key_result";

    // 最多可以选择多少张图片 - 默认9张
    private int mMaxCount = 9;
    // 选择图片的模式 - 默认单选
    private int mMode = MODE_SINGLE;
    // 是否显示拍照的相机
    private boolean mShowCamera = true;

    // 原始的图片
    private ArrayList<String> mOriginData;

    /**
     * 私有构造方法
     */
    private ImageSelector() {

    }

    public static ImageSelector create() {
        return new ImageSelector();
    }

    /**
     * 单选模式
     */
    public ImageSelector single() {
        mMode = MODE_SINGLE;
        return this;
    }

    /**
     * 多选模式
     */
    public ImageSelector multi() {
        mMode = MODE_MULTI;
        return this;
    }

    /**
     * 设置可以选多少张图片
     */
    public ImageSelector count(int count) {
        mMaxCount = count;
        return this;
    }

    /**
     * 是否显示相机
     */
    public ImageSelector showCamera(boolean showCamera) {
        mShowCamera = showCamera;
        return this;
    }

    /**
     * 原来选择好的图片
     */
    public ImageSelector origin(ArrayList<String> originList) {
        this.mOriginData = originList;
        return this;
    }

    /**
     * 启动执行 权限6.0自己需要去申请，也可以用我的权限申请框架
     */
    public void start(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ImageSelectActivity.class);
        addParamsByIntent(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动执行 权限6.0自己需要去申请，也可以用我的权限申请框架
     */
    public void start(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), ImageSelectActivity.class);
        addParamsByIntent(intent);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 给Intent添加参数
     *
     * @param intent
     */
    private void addParamsByIntent(Intent intent) {
        Bundle bundle = new Bundle();

        bundle.putInt(KEY_MAX_COUNT, mMaxCount);
        bundle.putBoolean(KEY_SHOW_CAMERA, mShowCamera);

        if (mOriginData != null && mMode == MODE_MULTI) {
            bundle.putSerializable(KEY_SELECT_LIST, mOriginData);
        }
        bundle.putInt(KEY_SELECT_MODE, mMode);

        intent.putExtra(ImageSelector.KEY_ARGS,bundle);
    }

}
