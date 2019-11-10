//
// Created by daner on 2017/8/2.
//

#include <jni.h>

#ifndef ESSAYJOKE_DIFFPATCH_H
#define ESSAYJOKE_DIFFPATCH_H

/**
 * 统一编译方式
 */
#ifdef __cplusplus
extern "C"{

#include "./bsdiff/bspatch.h"

#endif

JNIEXPORT void JNICALL
Java_com_qiyei_sdk_util_VersionManager_versionCombine(JNIEnv *env, jclass type, jstring oldApkPath_,
jstring newApkPath_, jstring patchPath_);

#ifdef __cplusplus
};
#endif

#endif //ESSAYJOKE_DIFFPATCH_H
