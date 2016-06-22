# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\androidSDK\sdk/tools/proguard/proguard-android.txt
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
-dontobfuscate
-keep class com.duanqu.qupai.jni.Releasable
-keep class com.duanqu.qupai.jni.ANativeObject
-dontwarn com.google.common.primitives.**
-dontwarn com.google.common.cache.**
-dontwarn com.google.auto.common.**
-dontwarn com.google.auto.factory.processor.**
-dontwarn com.fasterxml.jackson.**
-dontwarn net.jcip.annotations.**
-dontwarn javax.annotation.**
-dontwarn org.apache.http.client.utils.URIUtils
-keep class javax.annotation.** { *; }

-keep class * extends com.duanqu.qupai.jni.ANativeObject
-keep @com.duanqu.qupai.jni.AccessedByNative class *
-keep class com.duanqu.qupai.bean.DIYOverlaySubmit
-keep public interface com.duanqu.qupai.android.app.QupaiServiceImpl$QupaiService {*;}
-keep class com.duanqu.qupai.android.app.QupaiServiceImpl

-keep class com.duanqu.qupai.BeautySkinning
-keep class com.duanqu.qupai.render.BeautyRenderer
-keep public interface com.duanqu.qupai.render.BeautyRenderer$Renderer {*;}
-keepclassmembers @com.duanqu.qupai.jni.AccessedByNative class * {
    *;
}
-keepclassmembers class * {
    @com.duanqu.qupai.jni.AccessedByNative *;
}
-keepclassmembers class * {
    @com.duanqu.qupai.jni.CalledByNative *;
}

-keepclasseswithmembers class * {
    native <methods>;
}

-keepclassmembers class * {
    native <methods>;
}
-keepclassmembers class com.duanqu.qupai.** {
    *;
}

-keep class com.duanqu.qupai.recorder.EditorCreateInfo$VideoSessionClientImpl {
    *;
}
-keep class com.duanqu.qupai.recorder.EditorCreateInfo$SessionClientFctoryImpl {
    *;
}
-keep class com.duanqu.qupai.recorder.EditorCreateInfo{
    *;
}

-keepattributes Signature
-keepnames class com.fasterxml.jackson.** { *; }