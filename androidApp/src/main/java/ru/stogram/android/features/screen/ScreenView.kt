package ru.stogram.android.features.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import androidx.compose.foundation.ExperimentalFoundationApi
import com.rasalexman.sresult.common.extensions.logg
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.features.comments.CommentsView
import ru.stogram.android.features.login.Login
import ru.stogram.android.features.main.MainView
import ru.stogram.android.features.postdetails.PostDetailsView
import ru.stogram.android.features.profile.Profile
import ru.stogram.android.features.register.Register
import ru.stogram.android.features.subsnobserv.SubsAndObservers
import ru.stogram.android.navigation.Screen
import ru.stogram.android.navigation.composable
import ru.stogram.android.navigation.debugLabel

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@Composable
fun ScreenView() {
    val viewModel: ScreenViewModel = hiltViewModel()
    val navController = rememberAnimatedNavController().also {
        viewModel.setupNavHostController(it)
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
                startDestination = Screen.Login.route
            ) {

                composable(route = Screen.Login.route) {
                    Login()
                }

                composable(route = Screen.Register.route) {
                    Register()
                }

                composable(route = Screen.Main.route) {
                    MainView()
                }

                composable(
                    route = Screen.PostDetails.route,
                    debugLabel = Screen.PostDetails.route,
                    arguments = listOf(navArgument(ArgsNames.POST_ID) {
                        nullable = true
                        type = NavType.StringType
                    }, navArgument(ArgsNames.FROM_PROFILE) {
                        defaultValue = false
                        nullable = false
                        type = NavType.BoolType
                    })
                ) {
                    PostDetailsView()
                }

                composable(
                    route = Screen.PostComments.route,
                    debugLabel = Screen.PostComments.route,
                    arguments = listOf(navArgument(ArgsNames.POST_ID) {
                        nullable = true
                        type = NavType.StringType
                    })
                ) {
                    CommentsView()
                }

                composable(
                    route = Screen.UserProfile.route,
                    debugLabel = Screen.UserProfile.route,
                    arguments = listOf(navArgument(ArgsNames.USER_ID) {
                        nullable = true
                        type = NavType.StringType
                    }, navArgument(ArgsNames.USER_LOGIN) {
                        nullable = true
                        type = NavType.StringType
                    })
                ) {
                    Profile()
                }

                composable(
                    route = Screen.SubsAndObservers.route,
                    debugLabel = Screen.SubsAndObservers.route,
                    arguments = listOf(navArgument(ArgsNames.USER_ID) {
                        defaultValue = ""
                        nullable = true
                        type = NavType.StringType
                    }, navArgument(ArgsNames.SCREEN_TYPE) {
                        defaultValue = ""
                        nullable = true
                        type = NavType.StringType
                    })
                ) {
                    SubsAndObservers()
                }
            }
        }
    }
}