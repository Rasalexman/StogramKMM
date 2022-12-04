//------ APP VERSION
extra["appVersion"] = "1.0.1"

//------- LIBS VERSIONS
val kotlin_version = "1.7.20"
val gson = "2.9.1"
val leakcanary = "2.10"
val kotpref = "2.13.2"
val sresult = "1.3.59"
val sresultcore = "1.3.60"
val coil = "2.2.2"
val hiltVersion = "2.44"
val hiltAndroidXVersion = "1.0.0"

val junit = "5.8.1"
val runner = "1.1.0"
val espresso = "3.1.0"
val coroutines = "1.6.4"

val core = "1.9.0"
val lifecycle = "2.5.1"
val swiperefreshlayout = "1.2.0-alpha01"

val composeVersion = "1.3.1"
val composeTooling = "1.3.0"
val composeNavigation = "2.5.3"
val composeActivity = "1.6.1"
val accompanist = "0.28.0"

val ktorVersion = "2.0.0"
val datetimeVersion = "0.4.0"
val realmVersion = "1.5.0"
val coroutinesMt = "$coroutines-native-mt"
val serializationVersion = "1.3.2"

extra["composeVersion"] = composeVersion

//------ CONFIG DATA
extra["kotlinVersion"] = kotlin_version
extra["kotlinApiVersion"] = "1.7"
extra["jvmVersion"] = "11"
extra["agpVersion"] = "7.3.1"
extra["realmVersion"] = realmVersion
extra["composeNavigationVersion"] = composeNavigation
extra["hiltVersion"] = hiltVersion

extra["minSdkVersion"] = 24
extra["buildSdkVersion"] = 33

extra["jitpackPath"] = "https://jitpack.io"

//------- Libs path
extra["gson"] = "com.google.code.gson:gson:$gson"
extra["leakCanary"] = "com.squareup.leakcanary:leakcanary-android:$leakcanary"
extra["kotPref"] = "com.chibatching.kotpref:kotpref:$kotpref"
extra["coroutinesAndroid"] = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines"

//------- Android libs
extra["core"] = "androidx.core:core-ktx:$core"
extra["lifecycleVM"] = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle"
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
extra["composeTooling"] = "androidx.compose.ui:ui-tooling:$composeTooling"

//----- Accompanist
extra["companistFlowLayout"] = "com.google.accompanist:accompanist-flowlayout:$accompanist"
extra["companistInsetsUI"] = "com.google.accompanist:accompanist-insets-ui:$accompanist"
extra["companistNavigation"] = "com.google.accompanist:accompanist-navigation-animation:$accompanist"
extra["companistSwipeRefresh"] = "com.google.accompanist:accompanist-swiperefresh:$accompanist"
extra["companistPager"] = "com.google.accompanist:accompanist-pager:$accompanist"
extra["companistPagerIndicators"] = "com.google.accompanist:accompanist-pager-indicators:$accompanist"

//----- Hilt
extra["hiltAndroid"] = "com.google.dagger:hilt-android:$hiltVersion"
extra["hiltNavigation"] = "androidx.hilt:hilt-navigation-compose:$hiltAndroidXVersion"
extra["hiltCompiler"] = "com.google.dagger:hilt-compiler:$hiltVersion"

//------- iOS Libs
extra["ktorIOS"] = "io.ktor:ktor-client-darwin:$ktorVersion"

//------- Common Libs
extra["datetime"] = "org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion"
extra["ktorCore"] = "io.ktor:ktor-client-core:$ktorVersion"
extra["ktorCio"] = "io.ktor:ktor-client-cio:$ktorVersion"
extra["ktorLogging"] = "io.ktor:ktor-client-logging:$ktorVersion"
extra["realmBase"] = "io.realm.kotlin:library-base:$realmVersion"
// from maven local with ios artifacts
extra["sresultcore"] = "com.rasalexman.sresult:sresultcore:$sresultcore"
extra["coroutinesNative"] = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesMt"
extra["serializationCore"] = "org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion"

extra["junit"] = "org.junit.jupiter:junit-jupiter-api:$junit"
extra["junitAndroid"] = "androidx.test.ext:junit:1.1.3"
extra["runner"] = "androidx.test:runner:$runner"
extra["espresso"] = "androidx.test.espresso:espresso-core:$espresso"
