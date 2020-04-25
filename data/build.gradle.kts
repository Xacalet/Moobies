plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":domain"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.71")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    implementation("com.google.dagger:dagger:2.25.4")
}

