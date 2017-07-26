/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/7/23.
 * Version: 1.0
 * Description:
 */

#include "compress_image.h"
#include <string.h>
#include <android/bitmap.h>
#include <android/log.h>
#include <stdio.h>
#include <setjmp.h>
#include <math.h>
#include <stdint.h>
#include <time.h>

/**
 * 定义BYTE 类型为u_int8_t
 */
typedef u_int8_t BYTE;

char *error;

/**
 * error 结构体
 */
struct  error_mgr {
    struct jpeg_error_mgr pub;
    jmp_buf setjmp_buffer;
};

/**
 * 结构体类型定义
 */
typedef struct error_mgr *error_ptr;

/**
 * jpeg压缩图片
 */
jint compress_jpeg(BYTE * data,int width,int height,int quality,jboolean optimize,char * file_name){


}


JNIEXPORT jint JNICALL
Java_com_qiyei_sdk_util_ImageUtil_compressBitmap(JNIEnv *env, jclass type, jobject bitmap,
                                                 jint quality, jstring path_) {
    //1. 解析RGB
    //1.1 获取bitmap信息，w，h.format
    AndroidBitmapInfo bitmapInfo;
    //java你调用完方法往往返回的是对象，而C往往是参数
    AndroidBitmap_getInfo(env,bitmap,&bitmapInfo);

    //获取bitmap信息
    int bitmap_width = bitmapInfo.width;
    int bitmap_height = bitmapInfo.height;
    int bitmap_format = bitmapInfo.format;

    //对于不是ARGB8888格式的图片不支持
    if (bitmap_format != ANDROID_BITMAP_FORMAT_RGBA_8888){
        return -1;
    }

    LOG_W("bitmap,width = %d,height = %d",bitmap_width,bitmap_height);

    //1.2 把bitmap解析到数组中，数组中保存的是rgb --> YCbCr
    //1.2.2 锁定画布
    BYTE * pixel_color;
    //将bitmap数组导出到pixel_color中
    AndroidBitmap_lockPixels(env,bitmap,(void **) &pixel_color);

    //1.2.2 解析数据
    BYTE *data;
    BYTE r,g,b;
    //申请内存,因为不需要保存A通道，所以只需要申请 bitmap_width * bitmap_height * 3
    data = (BYTE *) malloc(bitmap_width * bitmap_height * 3);
    //数组指针指向首地址，因为这块内存需要释放，所以先保存下
    BYTE * head = data;

    int i = 0;
    int j = 0;
    int color;
    for (i = 0 ; i < bitmap_width ;i++){
        for (j = 0;j < bitmap_height;j++){
            color = *((int *)pixel_color);
            //将rgb取出来 ARGB 每个字节占八位
            r = (color & 0x00FF0000) >> 16;
            g = (color & 0x0000FF00) >> 8;
            b = (color & 0x000000FF) >> 0;

            //保存到data中
            *data = b;
            *(data + 1) = g;
            *(data + 2) = r;

            data = data + 3;
            // 一个像素点包括argb四个值，每+4下就是取下一个像素点
            pixel_color = pixel_color + 4;
        }
    }

    //1.2.3 解锁画布
    AndroidBitmap_unlockPixels(env,bitmap);

    //1.2.4 还差一个参数，jstring -> char*
    char * file_name = (char *)env->GetStringUTFChars(path_,NULL);

    int result = compress_jpeg(head,bitmap_width,bitmap_height,quality, true,file_name);

    //释放内存
    free(head);
    env->ReleaseStringUTFChars(path_,file_name);

    //释放bitmap,调用bitmap的recycle
    //找到Bitmap类
    jclass clazz = env->GetObjectClass(bitmap);

    jmethodID jmethod_ID = env->GetMethodID(clazz,"recycle","()V");
    //调用Bitmap的recycle()方法释放对象
    env->CallVoidMethod(bitmap,jmethod_ID);

    LOG_W("result = %d",result);

    //4 返回结果
    if (result == 0){
        return -1;
    }

    return 1;
}