@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidGradle) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.navSafeArgs) apply false
}

buildscript {
    /*dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }*/
    repositories {
        google()
        mavenCentral()
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KaptGenerateStubs>().configureEach {
    kotlinOptions {
        jvmTarget="1.8"
    }
}