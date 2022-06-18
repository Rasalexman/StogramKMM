package ru.stogram.android.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

fun NavController.toUserProfile(profileId: String) {
    val navRouter = "user/$profileId"
    this.navigateToBottomRouter(navRouter)
}

fun NavController.toPostDetails(postId: String) {
    val navRouter = "post/$postId"
    this.navigateToBottomRouter(navRouter)
}

fun NavController.navigateToBottomRouter(navRouter: String, withPopUp: Boolean = false) {
    this.navigate(route = navRouter) {
        launchSingleTop = true
        restoreState = true

        if(withPopUp) {
            popUpTo(this@navigateToBottomRouter.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }
}