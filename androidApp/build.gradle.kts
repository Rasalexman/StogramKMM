plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    val composeVersion: String by rootProject.extra
    val appVersion: String by rootProject.extra
    val minSdkVersion: Int by rootProject.extra
    val buildSdkVersion: Int by rootProject.extra

    compileSdk = buildSdkVersion
    defaultConfig {
        applicationId = "ru.stogram.android"
        minSdk = minSdkVersion
        targetSdk = buildSdkVersion
        versionCode = 1
        versionName = appVersion
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        languageVersion = "1.6"
        apiVersion = "1.6"
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
}

dependencies {
    val composeUI: String by rootProject.extra
    val composeMaterial: String by rootProject.extra
    val composePreview: String by rootProject.extra
    val composeActivity: String by rootProject.extra


    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    implementation(composeUI)
    implementation(composeMaterial)
    implementation(composePreview)
    implementation(composeActivity)
    debugImplementation("androidx.compose.ui:ui-tooling:1.1.1")
}