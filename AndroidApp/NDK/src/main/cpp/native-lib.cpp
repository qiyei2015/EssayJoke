#include <jni.h>
#include <string>
#include <android/log.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_qiyei_ndk_api_Demo_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {


    __android_log_print(ANDROID_LOG_INFO, "Demo", "Java_com_qiyei_ndk_api_Demo_stringFromJNI start");
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}