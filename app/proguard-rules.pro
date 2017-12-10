# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/howiezhang/tools/adt-bundle-mac-x86_64/sdk/tools/proguard/proguard-android.txt
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
-dontskipnonpubliclibraryclassmembers
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-ignorewarnings
-dontoptimize


-keep class com.google.gson.** {*;}
-keep class com.google.gson.examples.android.model.** { *; }
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

-keep class org.json.** {*;}

-keep public class * implements java.io.Serializable {
        public *;
}
-keepclassmembers class * implements android.os.Parcelable { *;}
-keep public class com.embarcadero.YHM_APP.R$*{
    public static final int *;
}
-keep class com.embarcadero.YHM_APP.database.db.** {*;}

-keep class com.facebook.**
#greendao3.2.0,此是针对3.2.0，如果是之前的，可能需要更换下包名
### greenDAO 3
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**

#友盟统计
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

