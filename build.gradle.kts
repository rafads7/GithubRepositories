@Suppress("DSL_SCOPE_VIOLATION")
plugins {
//androidGradlePlugin
    alias(libs.plugins.androidGradlePlugin) apply false
    alias(libs.plugins.androidLibrary) apply false
//kotlin compiler
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
//hilt
    id("com.google.dagger.hilt.android") version "2.48" apply false
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
/*buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.androidGradlePlugin)
        classpath(libs.Kotlin.gradlePlugin)
        classpath(libs.Jetpack.Navigation.navPlugin)
        classpath(libs.DependencyInjection.hiltPlugin)
    }
}*/


buildscript {
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
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