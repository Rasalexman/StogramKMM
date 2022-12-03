package ru.stogram.android.navigation

import androidx.navigation.NavHostController
import java.lang.ref.WeakReference

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

interface IMainRouter : IBackToHostRouter {
    fun setupMainNavHostController(navController: NavHostController)
}