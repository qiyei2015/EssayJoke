//
// Created by xiangwei.han on 2021/3/3.
//

#ifndef ESSAYJOKE_LOG_H
#define ESSAYJOKE_LOG_H

#include <android/log.h>

#define LOG_D(TAG,...) __android_log_print(ANDROID_LOG_DEBUG, TAG,  __VA_ARGS__)
#define LOG_I(TAG,...) __android_log_print(ANDROID_LOG_INFO, TAG,  __VA_ARGS__)
#define LOG_E(TAG,...) __android_log_print(ANDROID_LOG_ERROR, TAG,  __VA_ARGS__)

#endif //ESSAYJOKE_LOG_H
