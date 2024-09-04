plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

dependencies {

    implementation(project(mapOf("path" to ":domain")))

    implementation (libs.javax.inject)
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.kotlinx.coroutines.core)
    implementation(libs.androidx.paging.common.ktx)
    testImplementation(libs.junit)
    testImplementation (libs.mockito.kotlin)
    testImplementation (libs.mockito.inline)
    testImplementation (libs.kotlinx.coroutines.test)
}