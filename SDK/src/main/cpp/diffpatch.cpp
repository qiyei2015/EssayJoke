//
// Created by daner on 2017/8/2.
//

#include <cwchar>
#include "diffpatch.h"

JNIEXPORT void JNICALL
        Java_com_qiyei_sdk_util_VersionManager_versionCombine(JNIEnv *env, jclass type, jstring oldApkPath_,
                                                              jstring newApkPath_, jstring patchPath_){
// 1.封装参数
        int argc = 4;
        char * argv[4];
        // 1.1 转换  jstring -> char*
        char* old_pak_cstr = (char*)(env)->GetStringUTFChars(oldApkPath_,NULL);
        char* new_apk_cstr = (char*)(env)->GetStringUTFChars(newApkPath_,NULL);
        char* patch_cstr = (char*)(env)->GetStringUTFChars(patchPath_,NULL);
        // 第0的位置随便给
        argv[0] = "combine";
        argv[1] = old_pak_cstr;
        argv[2] = new_apk_cstr;
        argv[3] = patch_cstr;

        // 2.调用上面的方法  int argc,char * argv[]
        bspatch_main(argc,argv);

        // 3.释放资源
        (env)->ReleaseStringUTFChars(oldApkPath_,old_pak_cstr);
        (env)->ReleaseStringUTFChars(newApkPath_,new_apk_cstr);
        (env)->ReleaseStringUTFChars(patchPath_,patch_cstr);

}
