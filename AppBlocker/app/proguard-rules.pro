# Add project specific ProGuard rules here.
-keep class com.appblocker.** { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
