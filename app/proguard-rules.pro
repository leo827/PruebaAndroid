# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
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

#-injars      bin/classes
#-injars      libs
#-outjars     bin/classes-processed.jar

# Using Google's License Verification Library
-keepattributes SourceFile,LineNumberTable

# Specifies to write out some more information during processing.
# If the program terminates with an exception, this option will print out the entire stack trace, instead of just the exception message.
-verbose

####################################################################################################
##############################  IBM MobileFirst Platform configuration  ############################
####################################################################################################
# Annotations are represented by attributes that have no direct effect on the execution of the code.
-keepattributes *Annotation*

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepattributes InnerClasses
-keep class **.R$* {
    <fields>;
}

-keepclassmembers class Libraryes.**{
*;
}
-keepclassmembers class com.firemix.storeapp.**{
*;
}

-keep class com.firemix.storeapp.** {
*;
}

-keep class Librarys.** {
 *;
}

# These options let obfuscated applications or libraries produce stack traces that can still be deciphered later on
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Enable proguard with Google libs
-dontwarn com.google.common.**
-dontwarn com.google.ads.**
-dontwarn com.google.gms.**

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Remove debug logs in release build
-assumenosideeffects class android.util.Log {
    public static *** d(...);
}


-dontwarn android.support.v4.**
-dontwarn android.net.SSLCertificateSocketFactory
-keep class com.iydsa.apps.activos** {
 *;
}
-dontwarn com.squareup.okhttp.**

-keepattributes EnclosingMethod

-dontnote com.google.android.gms.internal.zzry


# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
-dontwarn butterknife.internal.**
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
-keep class **$$ViewBinder { *; }
-keep class **$ViewHolder { *; }
-keep class butterknife.**$Finder { *; }
-keep class **_ViewBinding { *; }


-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# android.app.Notification.setLatestEventInfo() was removed in
# Marsmallow, but is still referenced (safely)
-dontwarn com.google.android.gms.common.GooglePlayServicesUtil

#### -- Picasso --
 -dontwarn com.squareup.picasso.**

 #### -- OkHttp --

 -dontwarn com.squareup.okhttp.internal.**

 #### -- Apache Commons --

 -dontwarn org.apache.commons.logging.**

     -ignorewarnings
-keep class * {
public private protected *;
}



################################################################################