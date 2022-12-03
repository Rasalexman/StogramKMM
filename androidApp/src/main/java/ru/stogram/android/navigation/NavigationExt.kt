package ru.stogram.android.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

fun NavController.navigateToBottomRouter(
    navRouter: String,
    withPopUp: Boolean = false
) {
    this.navigate(route = navRouter) {
        launchSingleTop = true
        restoreState = true

        if(withPopUp) {
            val startDestination = this@navigateToBottomRouter.graph.findStartDestination()
            val destinationId = startDestination.id
            popUpTo(destinationId) {
                saveState = true
            }
        }
    }
}