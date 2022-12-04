plugins {
    id("com.android.application")
    kotlin("android")
//    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

val kotlinApiVersion: String by rootProject.extra
val jvmVersion: String by rootProject.extra

android {
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
        languageVersion = kotlinApiVersion
        apiVersion = kotlinApiVersion
        jvmTarget = jvmVersion
        freeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlin.Experimental"
        )
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }

    packagingOptions {
        resources.excludes.addAll(listOf("META-INF/AL2.0", "META-INF/LGPL2.1"))
    }
}

dependencies {
    val composeUI: String by rootProject.extra
    val composeMaterial: String by rootProject.extra
    val composePreview: String by rootProject.extra
    val composeActivity: String by rootProject.extra
    val composeNavigation: String by rootProject.extra
    val composeFonts: String by rootProject.extra
    val composeIcons: String by rootProject.extra
    val composeTooling: String by rootProject.extra

    val companistFlowLayout: String by rootProject.extra
    val companistInsetsUI: String by rootProject.extra
    val companistNavigation: String by rootProject.extra
    val companistSwipeRefresh: String by rootProject.extra
    val companistPager: String by rootProject.extra
    val companistPagerIndicators: String by rootProject.extra

    val hiltAndroid: String by rootProject.extra
    val hiltNavigation: String by rootProject.extra
    val hiltCompiler: String by rootProject.extra

    val core: String by rootProject.extra
    val lifecycleVM: String by rootProject.extra
    val coroutinesAndroid: String by rootProject.extra
    val realmBase: String by rootProject.extra
    val sresult: String by rootProject.extra

    val leakCanary: String by rootProject.extra
    val timber: String by rootProject.extra
    val coil: String by rootProject.extra
    val swiperefreshlayout: String by rootProject.extra

    implementation(project(":shared")) {
        exclude(group = "com.rasalexman.sresult")
    }
    implementation(core)
    implementation(lifecycleVM)
    implementation(coroutinesAndroid)
    implementation(timber)
    implementation(coil)
    implementation(sresult)
    compileOnly(realmBase)

    implementation(composeUI)
    implementation(swiperefreshlayout)
    implementation(composeMaterial)
    implementation(composePreview)
    implementation(composeActivity)
    implementation(composeNavigation)
    implementation(composeFonts)
    implementation(composeIcons)

    implementation(companistFlowLayout)
    implementation(companistInsetsUI)
    implementation(companistNavigation)
    implementation(companistSwipeRefresh)
    implementation(companistPager)
    implementation(companistPagerIndicators)

    // Hilt
    implementation(hiltAndroid)
    implementation(hiltNavigation)
    kapt(hiltCompiler)

    debugImplementation(leakCanary)
    debugImplementation(composeTooling)
}