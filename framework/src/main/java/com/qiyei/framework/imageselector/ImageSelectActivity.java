package com.qiyei.framework.imageselector;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.qiyei.framework.R;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.sdk.log.LogUtil;
import com.qiyei.sdk.view.xrecycler.XRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageSelectActivity extends BaseSkinActivity {

    // 选择图片的模式 - 多选
    public static final int MODE_MULTI = 0x0011;
    // 选择图片的模式 - 单选
    public static int MODE_SINGLE = 0x0012;
    // 是否显示相机的EXTRA_KEY
    public static final String EXTRA_SHOW_CAMERA = "EXTRA_SHOW_CAMERA";
    // 总共可以选择多少张图片的EXTRA_KEY
    public static final String EXTRA_SELECT_COUNT = "EXTRA_SELECT_COUNT";
    // 原始的图片路径的EXTRA_KEY
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "EXTRA_DEFAULT_SELECTED_LIST";
    // 选择模式的EXTRA_KEY
    public static final String EXTRA_SELECT_MODE = "EXTRA_SELECT_MODE";
    // 返回选择图片列表的EXTRA_KEY
    public static final String EXTRA_RESULT = "EXTRA_RESULT";

    private XRecyclerView mRecyclerView;
    /**
     *
     */
    private TextView mPreviewTv;
    /**
     *
     */
    private TextView mSelectNumTv;
    /**
     *
     */
    private TextView mSelectOkTv;

    private Bundle mSelectArgs;

    // 图片显示的Adapter
    private ImageSelectAdapter mImageAdapter;

    private LoaderManager.LoaderCallbacks<Cursor> mLoadCallBack = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID};


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            CursorLoader cursorLoader = new CursorLoader(ImageSelectActivity.this
                    ,MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    ,IMAGE_PROJECTION
                    ,IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR " + IMAGE_PROJECTION[3] + "=? "
                    ,new String[]{"image/jpeg", "image/png"}
                    ,IMAGE_PROJECTION[2] + " DESC");

            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null && data.getCount() > 0){
                List<ImageEntity> images = new ArrayList<>();
                data.moveToFirst();

                while (data.moveToNext()){
                    String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                    String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                    long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                    LogUtil.e("TAG", path + " " + name + " " + dateTime);

                    // 判断文件是不是存在
                    if (!pathExist(path)) {
                        continue;
                    }
                    // 封装数据对象
                    ImageEntity image = new ImageEntity(path, name, dateTime);
                    images.add(image);
                }

                // 显示列表数据
                showListData(images);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }

        /**
         * 判断该路径文件是不是存在
         */
        private boolean pathExist(String path) {
            if (!TextUtils.isEmpty(path)) {
                return new File(path).exists();
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_image_select);

        initData();
        initView();

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(SelectorConstant.IMAGE_SELECT,null,mLoadCallBack);
    }

    @Override
    protected void initView() {
        mRecyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        mPreviewTv = (TextView) findViewById(R.id.select_preview_tv);
        mSelectNumTv = (TextView) findViewById(R.id.select_num_tv);
        mSelectOkTv = (TextView) findViewById(R.id.select_ok_tv);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));

        mPreviewTv.setOnClickListener(this);
        mSelectOkTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (getIntent() != null){
            mSelectArgs = getIntent().getBundleExtra(SelectorConstant.ARGS);
        }
    }


    @Override
    public void onClick(View view) {

    }

    /**
     * 显示图片列表数据
     */
    private void showListData(List<ImageEntity> images) {
//        if (mImageAdapter == null) {
//            mImageAdapter = new ImageSelectorListAdapter(this, mResultList, mMaxCount, mMode);
//            mRecyclerView.setAdapter(mImageAdapter);
//        }
//        mImageAdapter.setData(images, mShowCamera);
//        mImageAdapter.setOnUpdateSelectListener(this);
    }

}
