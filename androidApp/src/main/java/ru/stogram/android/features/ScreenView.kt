package ru.stogram.android.features

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rasalexman.kodi.core.*
import com.rasalexman.sresult.common.extensions.logg
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.features.postdetails.PostDetailsView
import ru.stogram.android.features.profile.Profile
import ru.stogram.android.navigation.Screen
import ru.stogram.android.navigation.composable
import ru.stogram.android.navigation.debugLabel

@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun ScreenView() {
    val navController = rememberAnimatedNavController().also {
        kodi {
            unbind<NavHostController>()
            bind<NavHostController>() with provider { it }
        }
    }

    // Launch an effect to track changes to the current back stack entry, and push them
    // as a screen views to analytics
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { entry ->
            logg { "label: ${entry.debugLabel} | route: ${entry.destination.route}" }
        }
    }

    Scaffold { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(innerPadding)) {

            AnimatedNavHost(
                navController = navController,
                startDestination = Screen.Main.route
            ) {
                composable(route = Screen.Main.route) {
                    MainView()
                }

                composable(
                    route = Screen.PostDetails.route,
                    debugLabel = Screen.PostDetails.route,
                    arguments = listOf(navArgument(ArgsNames.POST_ID) {
                        nullable = true
                        type = NavType.StringType
                    })
                ) { backStackEntry ->
                    val postId = backStackEntry.arguments?.getString(ArgsNames.POST_ID)
                    PostDetailsView(postId)
                }

                composable(
                    route = Screen.UserProfile.route,
                    debugLabel = Screen.UserProfile.route,
                    arguments = listOf(navArgument(ArgsNames.USER_ID) {
                        nullable = true
                        type = NavType.StringType
                    })
                ) { backStackEntry ->
                    val userProfileId = backStackEntry.arguments?.getString(ArgsNames.USER_ID)
                    Profile(userProfileId)
                }
            }
        }
    }
}