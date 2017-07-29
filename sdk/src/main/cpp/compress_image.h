/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/7/23.
 * Version: 1.0
 * Description:
 */
#ifndef ESSAYJOKE_COMPRESS_IMAGE_H
#define ESSAYJOKE_COMPRESS_IMAGE_H

#include <jni.h>
#include <string>

/**
 * 统一编译方式
 */
#ifdef __cplusplus
extern "C"{
#endif

#include "jpeg/jpeglib.h"
#include "jpeg/cdjpeg.h"        /* Common decls for cjpeg/djpeg applications */
#include "jpeg/jversion.h"        /* for version message */
#include "jpeg/jconfig.h"

#ifdef __cplusplus
};
#endif


/**
 * Log打印
 */
#define LOG_TAG "jni"
#define LOG_W(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define LOG_I(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOG_E(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#define true 1
#define false 0


JNIEXPORT jint JNICALL
Java_com_qiyei_sdk_util_ImageUtil_compressBitmap(JNIEnv *env, jclass type, jobject bitmap,
                                                 jint quality, jstring path_);


#endif //ESSAYJOKE_COMPRESS_IMAGE_H
