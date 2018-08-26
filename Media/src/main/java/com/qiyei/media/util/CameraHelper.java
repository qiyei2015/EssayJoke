package com.qiyei.media.util;

import android.content.Context;
import android.hardware.Camera;
import android.view.Surface;
import android.view.WindowManager;

import com.qiyei.sdk.log.LogManager;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2018/8/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class CameraHelper {

    private static final String TAG = "CameraHelper";

    public static Camera.Size getOptimalVideoSize(List<Camera.Size> supportPreviewSizes,List<Camera.Size> supportedVideoSizes,int width, int height){

        // Use a very small tolerance because we want an exact match.
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) width / height;

        // Supported video sizes list might be null, it means that we are allowed to use the preview
        // sizes
        List<Camera.Size> videoSizes;
        if (supportedVideoSizes != null) {
            videoSizes = supportedVideoSizes;
        } else {
            videoSizes = supportPreviewSizes;
        }
        Camera.Size optimalSize = null;

        // Start with max value and refine as we iterate over available video sizes. This is the
        // minimum difference between view and camera height.
        double minDiff = Double.MAX_VALUE;

        // Target view height
        int targetHeight = height;

        // Try to find a video size that matches aspect ratio and the target view size.
        // Iterate over all available sizes and pick the largest size that can fit in the view and
        // still maintain the aspect ratio.
        for (Camera.Size size : videoSizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff && supportPreviewSizes.contains(size)) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find video size that matches the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : videoSizes) {
                if (Math.abs(size.height - targetHeight) < minDiff && supportPreviewSizes.contains(size)) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    /**
     * 设置显示方向
     * @param context
     * @return
     */
    public static int getDisplayRotation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int rotation = windowManager.getDefaultDisplay()
                .getRotation();
        switch (rotation) {
            case Surface.ROTATION_0: return 0;
            case Surface.ROTATION_90: return 90;
            case Surface.ROTATION_180: return 180;
            case Surface.ROTATION_270: return 270;
        }
        return 0;
    }

    /**
     * 设置摄像头角度
     * @param cameraId
     * @param camera
     * @param degrees
     */
    public static void setCameraDisplayOrientation(int cameraId, Camera camera,int degrees) {
        // See android.hardware.Camera.setCameraDisplayOrientation for
        // documentation.
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    /**
     *
     * @param cameraId
     * @param degree
     * @return
     */
    public static int getRecorderDegree(int cameraId,int degree){
        int angle = 0;
        /**
         * 后置
         */
        if (cameraId == 0) {
            switch (degree) {
                case 0:
                    angle = 90;
                    break;
                case 90:
                    angle = 90;
                    break;
                default:
                    angle = 90;
                    break;
            }
        } else if (cameraId == 1) {
            switch (degree) {
                case 0:
                    angle = 90;
                    break;
                case 90:
                    angle = 90;
                    break;
                default:
                    angle = 90;
                    break;
            }
        }
        LogManager.i(TAG, "record degree:" + angle);
        return angle;
    }
}
