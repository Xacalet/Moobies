import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId = "com.xacalet.moobies"
        minSdkVersion(26)
        targetSdkVersion(29)
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}")

    implementation("com.google.android.material:material:${Versions.material}")

    implementation("com.google.dagger:dagger:${Versions.dagger}")
    kapt("com.google.dagger:dagger-compiler:${Versions.dagger}")
    implementation("com.google.dagger:hilt-android:${Versions.dagger_hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.dagger_hilt}")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:${Versions.dagger_hilt_jetpack}")
    kapt("androidx.hilt:hilt-compiler:${Versions.dagger_hilt_jetpack}")

    implementation("com.squareup.retrofit2:retrofit:${Versions.moshi}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.moshi}")

    implementation("androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}")
    implementation("androidx.cardview:cardview:${Versions.AndroidX.cardView}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}")
    implementation("androidx.core:core-ktx:${Versions.AndroidX.core}")
    implementation("androidx.fragment:fragment-ktx:${Versions.AndroidX.fragment}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.navigation}")
    implementation("androidx.paging:paging-runtime:${Versions.AndroidX.pagination}")

    implementation("com.github.bumptech.glide:glide:${Versions.glide}")
    implementation("com.github.bumptech.glide:recyclerview-integration:${Versions.glide}")
    kapt("com.github.bumptech.glide:compiler:${Versions.glide}")

    testImplementation("junit:junit:${Versions.junit}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.AndroidX.test_ext}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.AndroidX.test_espresso}")
}
