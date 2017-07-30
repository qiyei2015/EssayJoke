package com.qiyei.sdk.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.qiyei.sdk.log.LogUtil;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/7/23.
 * Version: 1.0
 * Description:
 */
public class ImageUtil {

    private static final String TAG = "Image";

    /**
     * 左上
     */
    public static final int TOP_LEFT = 1;
    /**
     * 左下
     */
    public static final int BOTTOM_LEFT = 2;
    /**
     * 右上
     */
    public static final int TOP_RIRHT = 3;
    /**
     * 右下
     */
    public static final int BOTTOM_RIGHT = 4;
    /**
     * 中间
     */
    public static final int CENTER = 5;

    static {
        //加载lib库
        System.loadLibrary("jpeg");
        System.loadLibrary("compressimg");
    }

    /**
     * 缩放图片,根据给定的宽和高进行缩放
     * @param origin 原图
     * @param reqWidth 需要的宽
     * @param reqHeight 需要的高
     */
    public static Bitmap scaleImage(Bitmap origin,int reqWidth,int reqHeight){
        if (origin == null){
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();

        LogUtil.d(TAG,"scaleImage originWidth:" + width + ",originHeight:" + height);

        float scaleWidth = (float) (reqWidth * 1.0 / width);
        float scaleHeight = (float) (reqHeight * 1.0 / height);
        LogUtil.d(TAG,"scaleImage scaleWidth:" + scaleWidth + ",scaleHeight:" + scaleHeight);

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);

        Bitmap newBitmap = Bitmap.createBitmap(origin,0,0,width,height,matrix,true);
        if (!origin.isRecycled()){
            origin.recycle();
        }
        LogUtil.d(TAG,"scaleImage newWidth:" + newBitmap.getWidth() + ",newHeight:" + newBitmap.getHeight());
        return newBitmap;
    }

    /**
     * 按比例缩放图片
     * @param origin 原图
     * @param ratio 缩放比例
     */
    public static Bitmap scaleImage(Bitmap origin,float ratio){
        if (origin == null){
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();

        LogUtil.d(TAG,"scaleImage originWidth:" + width + ",originHeight:" + height);

        LogUtil.d(TAG,"scaleImage ratio:" + ratio);

        Matrix matrix = new Matrix();
        matrix.postScale(ratio,ratio);

        Bitmap newBitmap = Bitmap.createBitmap(origin,0,0,width,height,matrix,true);
        if (!origin.isRecycled()){
            origin.recycle();
        }
        LogUtil.d(TAG,"scaleImage newWidth:" + newBitmap.getWidth() + ",newHeight:" + newBitmap.getHeight());
        return newBitmap;
    }

    /**
     * 按比例缩放图片
     * @param origin 原图
     * @param ratio 缩放比例
     */
    /**
     * 裁剪图片
     * @param origin 原图
     * @param reqWidth 请求的宽
     * @param reqHeight 请求的高
     * @param direction 裁剪的方向
     * @return
     */
    public static Bitmap cropImage(Bitmap origin,int reqWidth,int reqHeight,int direction){
        if (origin == null){
            return null;
        }

        int width = origin.getWidth();
        int height = origin.getHeight();
        LogUtil.d(TAG,"cropImage originWidth:" + width + ",originHeight:" + height);

        if (!(reqWidth < width) || !(reqHeight < height)){
            LogUtil.e(TAG," reqWidth or reqHeight cannot be more than origin !");
            return null;
        }
        Bitmap newBitmap = null;
        switch (direction){
            case TOP_LEFT:
                newBitmap = Bitmap.createBitmap(origin,reqWidth,reqHeight
                        ,reqWidth,reqHeight,null,true);
                break;
            case BOTTOM_LEFT:
                newBitmap = Bitmap.createBitmap(origin,reqWidth,height - reqHeight
                        ,reqWidth,reqHeight,null,true);
                break;
            case TOP_RIRHT:
                newBitmap = Bitmap.createBitmap(origin,width - reqWidth,reqHeight
                        ,reqWidth,reqHeight,null,true);
                break;
            case BOTTOM_RIGHT:
                newBitmap = Bitmap.createBitmap(origin,width - reqWidth,height - reqHeight
                        ,reqWidth,reqHeight,null,true);
                break;
            case CENTER:
                newBitmap = Bitmap.createBitmap(origin,(width - reqWidth) / 2,(height - reqHeight) / 2
                        ,reqWidth,reqHeight,null,true);
                break;
            default:
                LogUtil.e(TAG," please check or direction,it must be correct params !");
                return null;
        }

        if (!origin.isRecycled()){
            origin.recycle();
        }
        LogUtil.d(TAG,"cropImage nnewWidth:" + newBitmap.getWidth() + ",newHeight:" + newBitmap.getHeight());
        return newBitmap;
    }

    /**
     * 旋转图片
     * @param origin 原图
     * @param degress 旋转角度
     */
    public static Bitmap rotateBitmap(Bitmap origin,float degress){
        if (origin == null){
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();

        LogUtil.d(TAG,"rotateBitmap originWidth:" + width + ",originHeight:" + height);

        LogUtil.d(TAG,"rotateBitmap degress:" + degress);

        Matrix matrix = new Matrix();
        matrix.setRotate(degress);

        Bitmap newBitmap = Bitmap.createBitmap(origin,0,0,width,height,matrix,true);
        if (!origin.isRecycled()){
            origin.recycle();
        }
        LogUtil.d(TAG,"rotateBitmap newWidth:" + newBitmap.getWidth() + ",newHeight:" + newBitmap.getHeight());
        return newBitmap;
    }

    /**
     * 图片偏移
     * @param origin
     * @param kx 偏移x
     * @param ky 偏移y
     * @return
     */
    public static Bitmap skewBitmap(Bitmap origin,float kx,float ky){
        if (origin == null){
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();

        LogUtil.d(TAG,"skewBitmap originWidth:" + width + ",originHeight:" + height);

        LogUtil.d(TAG,"skewBitmap kx:" + kx + ",ky:" + ky);

        Matrix matrix = new Matrix();
        matrix.postSkew(kx,ky);

        Bitmap newBitmap = Bitmap.createBitmap(origin,0,0,width,height,matrix,true);
        if (!origin.isRecycled()){
            origin.recycle();
        }
        LogUtil.d(TAG,"skewBitmap newWidth:" + newBitmap.getWidth() + ",newHeight:" + newBitmap.getHeight());
        return newBitmap;
    }

    /**
     * 压缩图片
     * @param bitmap
     * @param quality 需要压缩的质量 0 --100
     * @param path 图片文件路径
     */
    public static void compressImage(Bitmap bitmap,int quality,String path){
        jpegCompressBitmap(bitmap,quality,path);
    }

    /**
     * 压缩图片
     * @param bitmap
     * @param width
     * @param height
     * @param path
     */
    public static void compressImage(Bitmap bitmap,int width,int height,String path){
        jpegCompressBitmap2(bitmap,width,height,path);
    }

    /**
     * native压缩图片
     * @param bitmap
     * @param quality
     * @param path
     * @return
     */
    private native static int jpegCompressBitmap(Bitmap bitmap,int quality,String path);

    /**
     * native压缩图片,暂时有问题
     * @param bitmap
     * @param width
     * @param height
     * @param path
     * @return
     */
    private native static int jpegCompressBitmap2(Bitmap bitmap,int width,int height,String path);

}
