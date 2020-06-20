// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        jcenter()
        maven("http://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-alpha01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4-M1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.2.2")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("http://dl.bintray.com/kotlin/kotlin-eap")
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
