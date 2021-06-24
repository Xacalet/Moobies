plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "com.xacalet.moobies"
        minSdk = 24
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.core
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":net"))
    implementation(project(":utils"))

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:${Versions.java_desugar}") {
        because("Required for using Time API if minimum Android SDK is below 26")
    }

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}")

    implementation("com.google.android.material:material:${Versions.material}")

    implementation("com.google.dagger:hilt-android:${Versions.dagger}")
    implementation("androidx.hilt:hilt-common:${Versions.AndroidX.dagger_hilt}")
    implementation("androidx.hilt:hilt-navigation:${Versions.AndroidX.dagger_hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.dagger}")
    kapt("androidx.hilt:hilt-compiler:${Versions.AndroidX.dagger_hilt}")

    implementation("androidx.appcompat:appcompat:${Versions.AndroidX.appcompat}")
    implementation("androidx.core:core-ktx:${Versions.AndroidX.core}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}")
    implementation("androidx.room:room-ktx:${Versions.AndroidX.room}")
    implementation("androidx.room:room-runtime:${Versions.AndroidX.room}")
    kapt("androidx.room:room-compiler:${Versions.AndroidX.room}")

    // Jetpack compose
    implementation("androidx.activity:activity-compose:${Versions.Compose.activity}")
    implementation("androidx.compose.compiler:compiler:${Versions.Compose.core}")
    implementation("androidx.compose.foundation:foundation:${Versions.Compose.core}")
    implementation("androidx.compose.material:material:${Versions.Compose.core}")
    implementation("androidx.compose.material:material-icons-extended:${Versions.Compose.core}")
    implementation("androidx.compose.runtime:runtime-livedata:${Versions.Compose.core}")
    implementation("androidx.compose.ui:ui:${Versions.Compose.core}")
    implementation("androidx.compose.ui:ui-tooling:${Versions.Compose.core}")
    implementation("androidx.compose.ui:ui-util:${Versions.Compose.core}")
    implementation("androidx.constraintlayout:constraintlayout-compose:${Versions.Compose.constraintLayout}")
    implementation("androidx.hilt:hilt-navigation-compose:${Versions.Compose.hilt_navigation_compose}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.lifecycle_viewmodel}")
    implementation("androidx.navigation:navigation-compose:${Versions.Compose.navigation}")
    implementation("androidx.paging:paging-compose:${Versions.Compose.paging}")
    implementation("com.airbnb.android:lottie-compose:${Versions.Compose.lottie}")
    implementation("com.google.accompanist:accompanist-coil:${Versions.Compose.accompanist}")
    implementation("com.google.accompanist:accompanist-pager:${Versions.Compose.accompanist}")
    implementation("com.google.android.material:compose-theme-adapter:${Versions.Compose.theme_adapter}")

    testImplementation("androidx.arch.core:core-testing:${Versions.AndroidX.arch_core}")
    testImplementation("io.mockk:mockk:${Versions.mockk}")
    testImplementation("junit:junit:${Versions.junit}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlin_coroutines}")
    androidTestImplementation("androidx.compose.ui:ui-test:${Versions.Compose.core}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.Compose.core}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.AndroidX.test_ext}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.AndroidX.test_espresso}")
    androidTestImplementation("io.mockk:mockk-android:${Versions.mockk}")
}
