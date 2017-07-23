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

extern "C"

JNIEXPORT jint JNICALL
Java_com_qiyei_sdk_util_ImageUtil_compressBitmap(JNIEnv *env, jclass type, jobject bitmap,
                                                 jint quality, jstring path_);


#endif //ESSAYJOKE_COMPRESS_IMAGE_H
