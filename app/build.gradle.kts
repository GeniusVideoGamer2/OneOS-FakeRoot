plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.oneos.fakeroot"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.oneos.fakeroot"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}
