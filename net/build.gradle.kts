plugins {
    id("kotlin")
    kotlin("kapt")
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.dagger.dagger)

    api(libs.retrofit.retrofit)
    api(libs.retrofit.moshiConverter)

    implementation(libs.kotlin.stdlib)

    testImplementation(libs.junit)
}