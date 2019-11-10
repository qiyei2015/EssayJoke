package com.qiyei.media.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.qiyei.media.R;
import com.qiyei.media.ui.view.CameraPreview;
import com.qiyei.sdk.db.DaoSupportFactory;
import com.qiyei.sdk.db.IDaoSupport;
import com.qiyei.sdk.permission.PermissionFail;
import com.qiyei.sdk.permission.PermissionManager;
import com.qiyei.sdk.permission.PermissionSuccess;
import com.qiyei.sdk.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2018/8/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MediaRecordActivity extends Activity {

    /**
     * 需要的动态权限
     */
    private static final String[] VIDEO_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 权限码
     */
    private static final int PERMISSIONS_REQUEST_VIDEO = 1;

    private CameraPreview mCameraPreview;

    private Button mCaptureButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_record);
        mCaptureButton = findViewById(R.id.button);
        mCaptureButton.setText("录制视频");
        mCaptureButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mCameraPreview.isRecording()){
                    mCameraPreview.stopRecording();
                    mCaptureButton.setText("录制视频");
                }else {
                    mCameraPreview.startRecording();
                    mCaptureButton.setText("停止");
                }
            }
        });

        //检查权限申请
        PermissionManager.requestPermission(this,PERMISSIONS_REQUEST_VIDEO,VIDEO_PERMISSIONS);

        initCameraPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraPreview = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCameraPreview == null){
            initCameraPreview();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }


    /**
     * 初始化数据库
     */
    @PermissionSuccess(requestCode = PERMISSIONS_REQUEST_VIDEO)
    private void permissionSuccess(){
        initCameraPreview();
    }

    @PermissionFail(requestCode = PERMISSIONS_REQUEST_VIDEO)
    private void permissionFail(){
        ToastUtil.showLongToast("申请权限失败");
    }

    private void initCameraPreview(){
        mCameraPreview = new CameraPreview(this);
        FrameLayout layout = findViewById(R.id.camera_preview);
        layout.addView(mCameraPreview);
    }

}
