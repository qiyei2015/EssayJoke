/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/7/23.
 * Version: 1.0
 * Description:
 */

#include "compress_image.h"


JNIEXPORT jint JNICALL
Java_com_qiyei_sdk_util_ImageUtil_compressBitmap(JNIEnv *env, jclass type, jobject bitmap,
                                                 jint quality, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);

    // TODO

    env->ReleaseStringUTFChars(path_, path);
}