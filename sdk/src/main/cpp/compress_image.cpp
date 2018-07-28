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

///**
// * error 结构体
// */
//struct  error_mgr {
//    struct jpeg_error_mgr pub;
//    jmp_buf setjmp_buffer;
//};

///**
// * 结构体类型定义
// */
//typedef struct error_mgr *error_ptr;

/**
 * 错误退出函数
 */
//METHODDEF(void) error_exit(j_common_ptr info){

//    //获取j_common_ptr中的error
//    error_ptr  err = (error_ptr)info->err;
//    //调用error中的output_message输出打印信息
//    (*info->err->output_message)(info);
//    error = (char *)err->pub.jpeg_message_table[err->pub.msg_code];
//
//    LOG_E("jpeg error_exit,jpeg_message_table[%d]:%s",err->pub.msg_code,err->pub.jpeg_message_table[err->pub.msg_code]);
//
//    //
//    longjmp(err->setjmp_buffer,1);

//}

/**
 * jpeg压缩图片
 */
jint compress_jpeg(BYTE * data,int width,int height,int quality,jboolean optimize,char * file_name){

//    struct jpeg_compress_struct jcs;
//
//    //当读完整个文件的时候就会回调my_error_exit这个退出方法。
//    struct error_mgr jpeg_err;
//    jcs.err = jpeg_std_error(&jpeg_err.pub);
//    jpeg_err.pub.error_exit = error_exit;
//    //setjmp是洗衣歌系统级函数，是一个回调
//    if (setjmp(jpeg_err.setjmp_buffer)){
//        return -1;
//    }
//
//    //初始化jcs结构体
//    jpeg_create_compress(&jcs);
//    //打开输出文件 wb 可写 rb 可读,最终会把压缩的图像保存到该文件中
//    FILE *f = fopen(file_name,"wb");
//    if ( f == NULL){
//        return -1;
//    }
//
//    //设置jcs的文件路径以及宽高
//    jpeg_stdio_dest(&jcs,f);
//    jcs.image_width = width;
//    jcs.image_height = height;
//    jcs.arith_code = false; //true 算数编码，flase 霍夫曼编码
//    ///* 颜色的组成 rgb，三个 # of color components in input image */
//    int nComponent = 3;
//    jcs.input_components = nComponent; //颜色组成RGB
//    jcs.in_color_space = JCS_RGB;
//
//    jpeg_set_defaults(&jcs);
//    //是否采用霍夫曼编码
//    jcs.optimize_coding = optimize;
//    //设置质量
//    jpeg_set_quality(&jcs,quality,true);
//
//    //开始压缩
//    jpeg_start_compress(&jcs,TRUE);
//
//    JSAMPROW row_pointer[1];
//    int row_stride = jcs.image_width * nComponent;
//
//    //依次按照每一行循环扫描
//    while (jcs.next_scanline < jcs.image_height){
//        //得到一行的首地址。然后写入
//        row_pointer[0] = &data[jcs.next_scanline * row_stride];
//        jpeg_write_scanlines(&jcs,row_pointer,1);
//    }
//
//    //压缩结束
//    jpeg_finish_compress(&jcs);
//    //销毁回收内存
//    jpeg_destroy_compress(&jcs);
//    //关闭文件
//    fclose(f);

    return 0;
}

JNIEXPORT jint JNICALL
Java_com_qiyei_sdk_util_ImageUtil_jpegCompressBitmap(JNIEnv *env, jclass type, jobject bitmap,
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
    return result;
}


JNIEXPORT jint JNICALL
        Java_com_qiyei_sdk_util_ImageUtil_jpegCompressBitmap2(JNIEnv *env, jclass type, jobject bitmap,
                                                             jint width, jint height, jstring path_){
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

    int result = compress_jpeg(head,width,height,100, true,file_name);

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
    return result;
}

