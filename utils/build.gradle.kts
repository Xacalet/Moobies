plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = 33
    namespace = "com.xacalet.utils"

    defaultConfig {
        minSdk = 21
        version = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {

    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.lifecycle.livedata)
}