//------ APP VERSION
extra["appVersion"] = "1.0.1"

//------- LIBS VERSIONS
val kotlin_version = "1.6.10"
val gson = "2.8.9"
val navigation = "2.4.1"//"2.5.0-alpha01"
val kodi = "1.6.2"
val leakcanary = "2.8.1"
val kotpref = "2.13.2"
val junit = "5.8.1"
val runner = "1.1.0"
val espresso = "3.1.0"
val coroutines = "1.6.0"
val core: String = "1.7.0"
val composeVersion = "1.1.1"

val ktorVersion: String = "2.0.0"

extra["navigation"] = navigation
extra["composeVersion"] = composeVersion

//------ CONFIG DATA
extra["kotlinVersion"] = kotlin_version
extra["apiVersion"] = "1.6"
extra["jvmVersion"] = "11"
extra["agpVersion"] = "7.1.3"

extra["minSdkVersion"] = 23
extra["buildSdkVersion"] = 31

extra["jitpackPath"] = "https://jitpack.io"

//------- Libs path
extra["gson"] = "com.google.code.gson:gson:$gson"
extra["leakCanary"] = "com.squareup.leakcanary:leakcanary-android:$leakcanary"
extra["kodi"] = "com.github.Rasalexman.KODI:kodi:$kodi"
extra["kodiksp"] = "com.github.Rasalexman.KODI:kodiksp:$kodi"
extra["kotPref"] = "com.chibatching.kotpref:kotpref:$kotpref"
extra["coroutinesAndroid"] = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines"

//------- Android libs
extra["core"] = "androidx.core:core-ktx:$core"
extra["ktorAndroid"] = "io.ktor:ktor-client-android:$ktorVersion"

extra["composeUI"] = "androidx.compose.ui:ui:$composeVersion"
extra["composeMaterial"] = "androidx.compose.material:material:$composeVersion"
extra["composePreview"] = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
extra["composeActivity"] = "androidx.activity:activity-compose:1.4.0"

//------- iOS Libs
extra["ktorIOS"] = "io.ktor:ktor-client-darwin:$ktorVersion"

//------- Common Libs
extra["ktorCore"] = "io.ktor:ktor-client-core:$ktorVersion"
extra["ktorCio"] = "io.ktor:ktor-client-cio:$ktorVersion"
extra["ktorLogging"] = "io.ktor:ktor-client-logging:$ktorVersion"

extra["junit"] = "org.junit.jupiter:junit-jupiter-api:$junit"
extra["junitAndroid"] = "androidx.test.ext:junit:1.1.3"
extra["runner"] = "androidx.test:runner:$runner"
extra["espresso"] = "androidx.test.espresso:espresso-core:$espresso"
