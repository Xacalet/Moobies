plugins {
    id("kotlin")
    kotlin("kapt")
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation("com.google.dagger:dagger:${Versions.dagger}")

    api("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    api("com.squareup.retrofit2:converter-moshi:${Versions.retrofit}")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")

    testImplementation("junit:junit:${Versions.junit}")
}