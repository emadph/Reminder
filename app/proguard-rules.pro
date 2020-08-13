# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }



-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

###################################### butterknife #####################################
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }


###################################### Gson #####################################
##--- Begin:GSON ----
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# keep enum so gson can deserialize it
-keepclassmembers enum * { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class net.mreunionlabs.wob.model.request.** { *; }
-keep class net.mreunionlabs.wob.model.response.** { *; }
-keep class net.mreunionlabs.wob.model.gson.** { *; }
##--- End:GSON ----
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
##################################### RxJava #####################################
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent


# ALSO REMEMBER KEEPING YOUR MODEL CLASSES
-keep class com.your.package.model.** { *; }
#################################### RxBus #####################################
-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.anadeainc.rxbus.Subscribe public *;
}
#################################### okhttp #####################################
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn org.conscrypt.**


#################################### firebase #####################################

-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

-keep class com.google.firebase.quickstart.database.java.viewholder.** {
    *;
}

-keepclassmembers class com.google.firebase.quickstart.database.java.models.** {
    *;
}

## -------------Begin: Retrofit2 ---
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclassmembernames interface * {
        @retrofit.http.* <methods>;
}

## -------------End: Retrofit2 ---


-dontwarn android.support.v7.**
-keep class android.support.v7.**
-keep interface android.support.v7.*
-keepattributes *Annotation,Signature
-dontwarn com.github.siyamed.**
-keep class com.github.siyamed.shapeimageview.**

-dontwarn ss.com.**

