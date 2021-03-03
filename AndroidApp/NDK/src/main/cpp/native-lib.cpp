
#include <jni.h>
#include <string>
#include "log.h"

#define LOG_TAG "JNIDemo"


extern "C" JNIEXPORT jstring JNICALL Java_com_qiyei_ndk_api_Demo_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {

    LOG_D(LOG_TAG,"Java_com_qiyei_ndk_api_Demo_stringFromJNI start");
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL Java_com_qiyei_ndk_api_Demo_operateString(
        JNIEnv* env,
        jobject /* this */,jstring str) {

    //获取，得到指针
    const char *strPtr = env->GetStringUTFChars(str,NULL);
    if (strPtr == NULL){
        return NULL;
    }

    char buff[128] = {0};
    strcpy(buff,strPtr);
    LOG_I(LOG_TAG,"Java_com_qiyei_ndk_api_Demo_operateString start,args=%s",buff);

    strcat(buff," 1234567890");
    //释放
    env->ReleaseStringUTFChars(str,strPtr);

    return env->NewStringUTF(buff);
}