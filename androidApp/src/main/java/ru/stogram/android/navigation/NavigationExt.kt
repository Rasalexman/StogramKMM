package ru.stogram.android.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

fun NavController.toUserProfile(profileId: String) {
    val navRouter = "user/$profileId"
    this.navigateToRouter(navRouter)
}

fun NavController.toPostDetails(postId: String) {
    val navRouter = "post/$postId"
    this.navigateToRouter(navRouter)
}

fun NavController.toPostComments(postId: String) {
    val navRouter = "post/comments/$postId"
    this.navigateToRouter(navRouter)
}

fun NavController.toMainScreen() {
    val navRouter = Screen.Main.route
    this.navigate(route = navRouter) {
        launchSingleTop = true
        restoreState = true
        popUpTo(Screen.Login.route) {
            inclusive = true
        }
    }
}

fun NavController.toRegisterScreen() {
    val navRouter = Screen.Register.route
    this.navigateToRouter(navRouter)
}

fun NavController.toLoginScreen() {
    val navRouter = Screen.Login.route
    this.navigate(route = navRouter) {
        launchSingleTop = true
        restoreState = true
        popUpTo(Screen.Main.route) {
            inclusive = true
        }
    }
}

fun NavController.navigateToRouter(
    navRouter: String,
    withPopUp: Boolean = false
) {
    this.navigate(route = navRouter) {
        launchSingleTop = true
        restoreState = true

        if(withPopUp) {
            val startDestination = this@navigateToRouter.graph.findStartDestination()
            val destinationId = startDestination.id
            popUpTo(destinationId) {
                saveState = true
            }
        }
    }
}