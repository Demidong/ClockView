# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/demi/Library/Android/sdk/tools/proguard/proguard-android.txt
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

#指定代码的压缩级别
-optimizationpasses 5
#混淆时是否记录日志
-verbose
#输出错误信息行号
-renamesourcefileattribute SourceFile
#输出错误信息行号
-keepattributes SourceFile,LineNumberTable

-keepattributes *Annotation*
#泛型
-keepattributes Signature

-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

#为了支持在layout中写android:onclick="[method]"
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

#为了支持在layout中写自定义view
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class **.R$* {
    public static final int *;
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
    public <init>(org.json.JSONObject);
}

#butterknife
-keep class butterknife.** {*;}
-dontnote butterknife.internal.**
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder {*;}

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#FMAgent 同盾
-dontwarn android.os.**
-dontwarn com.android.internal.**
-keep class cn.fraudmetrix.sdk.**{*;}

#umeng
-keep public class com.aijifu.mmbj.R$*{
    public static final int *;
}
-keep class com.umeng.onlineconfig.OnlineConfigAgent {
        public <fields>;
        public <methods>;
}
-keep class com.umeng.onlineconfig.OnlineConfigLog {
        public <fields>;
        public <methods>;
}
-keep interface com.umeng.onlineconfig.UmengOnlineConfigureListener {
        public <methods>;
}
-keep public class com.umeng.fb.ui.ThreadView {
}
-keep public class * extends com.umeng.**
-keep class com.umeng.** { *; }


-keep class android.support.** { *; }
-keep interface android.support.**{ *; }
-keep class bolts.** { *; }
-keep class ch.imvs.sdes4j.** { *; }
-keep class com.activeandroid.** { *; }
-keep class com.bigkoo.** { *; }
-keep class com.facebook.rebound.** { *; }
-keep class com.github.mikephil.charting.** { *; }
-keep class com.google.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.hannesdorfmann.parcelableplease.** { *; }
-keep class com.igexin.** { *; }
-keep class com.nostra13.universalimageloader.** { *; }
-keep interface com.nostra13.universalimageloader.** { *; }
-keep class com.novell.sasl.client.** { *; }
-keep class com.parse.** { *; }
-keep class com.squareup.** { *; }
-keep interface com.squareup.** { *; }
-keep class com.ta.utdid2.** { *; }
-keep class com.zhy.** { *; }
-keep class de.hdodenhof.circleimageview.** { *; }
-keep class info.hoang8f.** { *; }
-keep class io.vov.vitamio.** { *; }
-keep class org.android.agoo.** { *; }
-keep class org.jivesoftware.** { *; }
-keep class org.joda.time.** { *; }
-keep class sun.misc.Unsafe { *; }
-keep class com.zcw.togglebutton.** { *; }
-keep class com.viewpagerindicator.** { *; }
-keep class org.slf4j.** { *; }
-keep class rx.internal.util.** {*;}

-dontwarn ch.imvs.sdes4j.**
-dontwarn com.google.**
-dontwarn com.parse.**
-dontwarn com.squareup.**
-dontwarn com.viewpagerindicator.**
-dontwarn com.zcw.togglebutton.**
-dontwarn okio.**
-dontwarn org.joda.time.**
-dontwarn org.slf4j.**
-dontwarn retrofit.**
-dontwarn rx.**

#-obfuscationdictionary proguard-keyword.txt

-keep class com.tencent.tinker.** {*;}
-keep class com.networkbench.agent.** {*;}
-dontwarn  com.tencent.tinker.**


-keepclassmembers class * extends android.widget.BaseAdapter {
    public <init>(android.content.Context);
}

-dontwarn com.baidao.chart.**
-dontwarn com.easemob.chat.**

-keep class com.easemob.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
-dontwarn  com.easemob.**
#2.0.9后的不需要加下面这个keep
#-keep class org.xbill.DNS.** {*;}
#另外，demo中发送表情的时候使用到反射，需要keep SmileUtils
#注意前面的包名，如果把这个类复制到自己的项目底下，比如放在com.example.utils底下，应该这么写(实际要去掉#)
#-keep class com.example.utils.SmileUtils {*;}
#如果使用easeui库，需要这么写


#okhttputils
-dontwarn com.zhy.http.**


#okhttp
-dontwarn okhttp3.**


#okio
-dontwarn okio.**



# Retrofit
-dontwarn retrofit2.**
# sharesdk
-dontwarn cn.sharesdk.**
