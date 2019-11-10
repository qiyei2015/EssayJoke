package com.qiyei.framework.imageselector;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.qiyei.framework.R;
import com.qiyei.framework.activity.BaseSkinActivity;
import com.qiyei.sdk.log.LogManager;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageSelectActivity extends BaseSkinActivity implements ImageSelectAdapter.UpdateSelectListener{

    private RecyclerView mRecyclerView;
    /**
     * 预览按钮
     */
    private TextView mPreviewTv;
    /**
     * 显示所选张数
     */
    private TextView mSelectNumTv;
    /**
     *确定按钮
     */
    private TextView mSelectOkTv;
    /**
     * 图片显示的Adapter
     */
    private ImageSelectAdapter mImageAdapter;
    /**
     * 图片选择器的参数
     */
    private Bundle mSelectArgs;
    /**
     * 模式，单选还是多选
     */
    private int mMode = ImageSelector.MODE_SINGLE;
    /**
     * 最多可选张数
     */
    private int mMaxCount = 1;
    /**
     * 是否显示相机
     */
    private boolean isShowCamera = true;
    /**
     * 选择的图片结果
     */
    private List<String> mResultList;
    /**
     * 打开相机时的临时文件路径
     */
    private String mTempFilePath;


    private String TEMP_KEY = "temp_key";

    /**
     * 加载图片的回调
     */
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
                    LogManager.w(ImageSelector.TAG, path + "  " + name + "  " + dateTime);

                    // 判断文件是不是存在
                    if (!pathExist(path)) {
                        continue;
                    }
                    // 封装数据对象
                    ImageEntity image = new ImageEntity(path, name, dateTime);
                    images.add(image);
                }

                LogManager.d(ImageSelector.TAG,"images:" + images.size());

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
        if (savedInstanceState != null){
            mTempFilePath = savedInstanceState.getString(TEMP_KEY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(TEMP_KEY,mTempFilePath);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_image_select);

        initData();
        initView();

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(ImageSelector.IMAGE_SELECT,null,mLoadCallBack);
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mPreviewTv = (TextView) findViewById(R.id.select_preview_tv);
        mSelectNumTv = (TextView) findViewById(R.id.select_num_tv);
        mSelectOkTv = (TextView) findViewById(R.id.select_ok_tv);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));

        mSelectNumTv.setText( "0/" + mMaxCount);

        mPreviewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageSelectActivity.this,ImagePreviewActivity.class);
                intent.putExtra(ImageSelector.KEY_RESULT,(Serializable) mResultList);
                startActivity(intent);
            }
        });

        mSelectOkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult();
            }
        });
    }

    @Override
    protected void initData() {
        if (getIntent() != null){
            mSelectArgs = getIntent().getBundleExtra(ImageSelector.KEY_ARGS);
            if (mSelectArgs != null){
                mMode = mSelectArgs.getInt(ImageSelector.KEY_SELECT_MODE,ImageSelector.MODE_SINGLE);
                mMaxCount = mSelectArgs.getInt(ImageSelector.KEY_MAX_COUNT,1);
                isShowCamera = mSelectArgs.getBoolean(ImageSelector.KEY_SHOW_CAMERA,true);
                mResultList = mSelectArgs.getStringArrayList(ImageSelector.KEY_SELECT_LIST);
            }
        }
        if (mResultList == null){
            mResultList = new ArrayList<>();
        }
    }


    @Override
    public void onClick(View view) {

    }

    /**
     * 选择图片
     */
    @Override
    public void imageSelect() {
        mSelectNumTv.setText(mResultList.size() + "/" + mMaxCount);
    }

    /**
     * 打开相机
     * @param file
     */
    @Override
    public void openCamera(File file) {
        mTempFilePath = file.getAbsolutePath();
        LogManager.d(TAG,"file:" + file.getAbsolutePath());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, ImageSelector.REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == ImageSelector.REQUEST_CAMERA) {
                // notify system the image has change
                LogManager.d(TAG,"filepath:" + mTempFilePath);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(mTempFilePath))));
                mResultList.add(mTempFilePath);
                setResult();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 显示图片列表数据
     */
    private void showListData(List<ImageEntity> images) {
        if (mImageAdapter == null) {
            mImageAdapter = new ImageSelectAdapter(this, mResultList,images, mMaxCount, mMode);
            mRecyclerView.setAdapter(mImageAdapter);
        }
        mImageAdapter.setShowCamera(isShowCamera);
        mImageAdapter.setOnUpdateSelectListener(this);
    }

    /**
     * 设置返回结果
     */
    private void setResult() {
        Intent data = new Intent();
        data.putExtra(ImageSelector.KEY_RESULT,(Serializable) mResultList);
        setResult(RESULT_OK, data);
        finish();
    }

}
