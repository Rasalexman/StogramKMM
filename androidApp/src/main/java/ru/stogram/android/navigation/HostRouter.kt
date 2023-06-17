package ru.stogram.android.navigation

import androidx.navigation.NavHostController
import ru.stogram.models.IUser
import java.lang.ref.WeakReference

abstract class HostRouter : IHostRouter {
    private var navHostControllerRef: WeakReference<NavHostController?> = WeakReference(null)
    private val navHostControllerInstance: NavHostController
        get() = checkNotNull(navHostControllerRef.get())

    override fun setupNavHostController(navHostController: NavHostController) {
        navHostControllerRef = WeakReference(navHostController)
    }

    override fun showHostUserProfile(user: IUser) {
        val navRouter = "user/${user.id}/${user.login}"
        navHostControllerInstance.navigateToBottomRouter(navRouter)
    }

    override fun showHostPostDetails(postId: String, fromProfile: Boolean) {
        val navRouter = "post/$postId/$fromProfile"
        navHostControllerInstance.navigateToBottomRouter(navRouter)
    }

    override fun showHostPostComments(postId: String) {
        val navRouter = "post/comments/$postId"
        navHostControllerInstance.navigateToBottomRouter(navRouter)
    }

    override fun showSubsAndObserversScreen(userId: String?, screenType: String) {
        val navRouter = "user/subobs/$userId/$screenType"
        navHostControllerInstance.navigateToBottomRouter(navRouter)
    }

    override fun popBackToHost() {
        navHostControllerInstance.popBackStack()
    }

    override fun showHostMainScreen() {
        val navRouter = Screen.Main.route
        navHostControllerInstance.navigate(route = navRouter) {
            launchSingleTop = true
            restoreState = true
            popUpTo(Screen.Login.route) {
                inclusive = true
            }
        }
    }

    override fun showHostRegisterScreen() {
        navHostControllerInstance.navigate(route = Screen.Register.route)
    }

    override fun showHostLoginScreen() {
        val navRouter = Screen.Login.route
        navHostControllerInstance.navigate(route = navRouter) {
                launchSingleTop = true
                restoreState = true
            popUpTo(Screen.Main.route) {
                inclusive = true
            }
        }
    }
}

interface IHostRouter : IBackToHostRouter {
    fun setupNavHostController(navHostController: NavHostController)
    fun showHostUserProfile(user: IUser)
    fun showHostPostComments(postId: String)
    fun showHostPostDetails(postId: String, fromProfile: Boolean = false)

    fun showHostMainScreen()
    fun showHostRegisterScreen()
    fun showHostLoginScreen()
    fun showSubsAndObserversScreen(userId: String?, screenType: String)
}