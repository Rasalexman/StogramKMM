package ru.stogram.android.navigation

import androidx.navigation.NavHostController
import java.lang.ref.WeakReference

class AppRouter : MainHostRouter(), IAppRouter {

}

abstract class MainHostRouter : HostRouter(), IMainRouter {
    private var mainNavControllerRef: WeakReference<NavHostController?> = WeakReference(null)
    @Suppress("unused")
    private val mainNavControllerInstance: NavHostController
        get() = checkNotNull(mainNavControllerRef.get())

    /**
     * Restricted function
     */
    override fun setupMainNavHostController(navController: NavHostController) {
        mainNavControllerRef = WeakReference(navController)
    }
}

abstract class HostRouter : IHostRouter {
    private var navHostControllerRef: WeakReference<NavHostController?> = WeakReference(null)
    private val navHostControllerInstance: NavHostController
        get() = checkNotNull(navHostControllerRef.get())

    override fun setupNavHostController(navHostController: NavHostController) {
        navHostControllerRef = WeakReference(navHostController)
    }

    override fun showHostUserProfile(profileId: String) {
        navHostControllerInstance.toUserProfile(profileId)
    }

    override fun showHostPostDetails(postId: String) {
        navHostControllerInstance.toPostDetails(postId)
    }

    override fun showHostPostComments(postId: String) {
        navHostControllerInstance.toPostComments(postId)
    }

    override fun popBackToHost() {
        navHostControllerInstance.popBackStack()
    }

}

interface IAppRouter : IHostRouter, IMainRouter

interface IMainRouter : IBackToHostRouter {
    fun setupMainNavHostController(navController: NavHostController)
}

interface IHostRouter : IBackToHostRouter {
    fun setupNavHostController(navHostController: NavHostController)

    fun showHostUserProfile(profileId: String)
    fun showHostPostComments(postId: String)
    fun showHostPostDetails(postId: String)
}

interface IBackToHostRouter {
    fun popBackToHost()
}