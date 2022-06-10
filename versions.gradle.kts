//------ APP VERSION
extra["appVersion"] = "1.0.1"

//------- LIBS VERSIONS
val kotlin_version = "1.6.21"
val gson = "2.9.0"
val kodi = "1.6.4"
val leakcanary = "2.9.1"
val kotpref = "2.13.2"
val sresult = "1.3.47"
val coil = "2.1.0"

val junit = "5.8.1"
val runner = "1.1.0"
val espresso = "3.1.0"
val coroutines = "1.6.1"

val core = "1.8.0"
val lifecycle = "2.5.0-rc01"
val swiperefreshlayout = "1.2.0-alpha01"

val composeVersion = "1.2.0-beta03"
val composeNavigation = "2.5.0-rc01"
val composeActivity = "1.5.0-rc01"
val accompanist = "0.24.6-alpha"

val ktorVersion = "2.0.0"
val datetimeVersion = "0.3.2"
val realmVersion = "1.0.0"
val coroutinesMt = "1.6.1-native-mt"
val serializationVersion = "1.3.2"

extra["composeVersion"] = composeVersion

//------ CONFIG DATA
extra["kotlinVersion"] = kotlin_version
extra["apiVersion"] = "1.6"
extra["jvmVersion"] = "11"
extra["agpVersion"] = "7.2.1"
extra["realmVersion"] = realmVersion

extra["minSdkVersion"] = 24
extra["buildSdkVersion"] = 32

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
extra["lifecycleVM"] = "androidx.lifecycle:lifecycle-viewmodel:$lifecycle"
extra["ktorAndroid"] = "io.ktor:ktor-client-android:$ktorVersion"
extra["sresult"] = "com.github.Rasalexman.SResult:sresult:$sresult"
extra["timber"] = "com.jakewharton.timber:timber:5.0.1"
extra["coil"] = "io.coil-kt:coil-compose:$coil"
extra["swiperefreshlayout"] = "androidx.swiperefreshlayout:swiperefreshlayout:$swiperefreshlayout"

//------- Compose
extra["composeUI"] = "androidx.compose.ui:ui:$composeVersion"
extra["composeMaterial"] = "androidx.compose.material:material:$composeVersion"
extra["composeNavigation"] = "androidx.navigation:navigation-compose:$composeNavigation"
extra["composePreview"] = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
extra["composeActivity"] = "androidx.activity:activity-compose:$composeActivity"
extra["composeFonts"] = "androidx.compose.ui:ui-text-google-fonts:$composeVersion"
extra["composeIcons"] = "androidx.compose.material:material-icons-extended:$composeVersion"

//----- Accompanist
extra["companistFlowLayout"] = "com.google.accompanist:accompanist-flowlayout:$accompanist"
extra["companistInsetsUI"] = "com.google.accompanist:accompanist-insets-ui:$accompanist"
extra["companistNavigation"] = "com.google.accompanist:accompanist-navigation-animation:$accompanist"
extra["companistSwipeRefresh"] = "com.google.accompanist:accompanist-swiperefresh:$accompanist"
extra["companistPager"] = "com.google.accompanist:accompanist-pager:$accompanist"
extra["companistPagerIndicators"] = "com.google.accompanist:accompanist-pager-indicators:$accompanist"

//------- iOS Libs
extra["ktorIOS"] = "io.ktor:ktor-client-darwin:$ktorVersion"

//------- Common Libs
extra["datetime"] = "org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion"
extra["ktorCore"] = "io.ktor:ktor-client-core:$ktorVersion"
extra["ktorCio"] = "io.ktor:ktor-client-cio:$ktorVersion"
extra["ktorLogging"] = "io.ktor:ktor-client-logging:$ktorVersion"
extra["realmBase"] = "io.realm.kotlin:library-base:$realmVersion"
extra["coroutinesNative"] = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesMt"
extra["serializationCore"] = "org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion"

extra["junit"] = "org.junit.jupiter:junit-jupiter-api:$junit"
extra["junitAndroid"] = "androidx.test.ext:junit:1.1.3"
extra["runner"] = "androidx.test:runner:$runner"
extra["espresso"] = "androidx.test.espresso:espresso-core:$espresso"
