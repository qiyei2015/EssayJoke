package com.qiyei.ndk.api

class Demo {

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun stringFromJNI(): String

    external fun operateString(msg: String): String
}