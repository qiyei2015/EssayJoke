# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#ProGuard不要警告找不到xx.xx.xxx.**这个包里面的类的相关引用
#保持xx.xx.xx.**这个包里面的所有类和所有方法而不混淆
-dontwarn okhttp3.**
-keep class okhttp3.** {*;}

-dontwarn okio.**
-keep class okio.** {*;}

-dontwarn retrofit2.**
-keep class retrofit2.** {*;}

-dontwarn org.greenrobot.greendao.**
-keep class org.greenrobot.greendao.** { *;}

-ignorewarnings