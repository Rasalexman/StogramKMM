package ru.stogram.android.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.navigation
import com.rasalexman.kodi.core.immutableInstance
import ru.stogram.android.features.home.HomeView
import ru.stogram.android.features.home.HomeViewModel
import ru.stogram.android.features.reactions.ReactionsView
import ru.stogram.android.features.reactions.ReactionsViewModel
import ru.stogram.android.features.search.SearchView
import ru.stogram.android.features.search.SearchViewModel

object ScreenNames {
    const val HOME = "home"
    const val SEARCH = "search"
    const val CREATE = "create"
    const val REACTIONS = "reactions"
    const val PROFILE = "profile"
}

internal sealed class Screen(val route: String) {
    object Home : Screen(ScreenNames.HOME)
    object Search : Screen(ScreenNames.SEARCH)
    object Create : Screen(ScreenNames.CREATE)
    object Reactions : Screen(ScreenNames.REACTIONS)
    object Profile : Screen(ScreenNames.PROFILE)
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"
    object Home : LeafScreen(ScreenNames.HOME)
    object Search : LeafScreen(ScreenNames.SEARCH)
    object Reactions : LeafScreen(ScreenNames.REACTIONS)
}

@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { defaultStogramEnterTransition(initialState, targetState) },
        exitTransition = { defaultStogramExitTransition(initialState, targetState) },
        popEnterTransition = { defaultStogramPopEnterTransition() },
        popExitTransition = { defaultStogramPopExitTransition() },
        modifier = modifier,
    ) {
        addHomeTopLevel(navController)
        addSearchTopLevel(navController)
        addReactionsTopLevel(navController)
        //addWatchedTopLevel(navController, onOpenSettings)
        //addSearchTopLevel(navController, onOpenSettings)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addHomeTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Home.createRoute(Screen.Home),
    ) {
        addHome(navController, Screen.Home)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addHome(
    navController: NavController,
    root: Screen,
) {
    composable(
        route = LeafScreen.Home.createRoute(root),
        debugLabel = "Home()",
    ) {
        val vm: HomeViewModel by immutableInstance()
        HomeView(viewModel = vm)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSearchTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.Search.route,
        startDestination = LeafScreen.Search.createRoute(Screen.Search),
    ) {
        addSearch(navController, Screen.Search)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSearch(
    navController: NavController,
    root: Screen,
) {
    composable(
        route = LeafScreen.Search.createRoute(root),
        debugLabel = "Search()",
    ) {
        val vm: SearchViewModel by immutableInstance()
        SearchView(viewModel = vm)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addReactionsTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.Reactions.route,
        startDestination = LeafScreen.Reactions.createRoute(Screen.Reactions),
    ) {
        addReactions(navController, Screen.Reactions)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addReactions(
    navController: NavController,
    root: Screen,
) {
    composable(
        route = LeafScreen.Reactions.createRoute(root),
        debugLabel = "Reactions()",
    ) {
        val vm: ReactionsViewModel by immutableInstance()
        ReactionsView(viewModel = vm)
    }
}


@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultStogramEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeIn()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Start)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultStogramExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeOut()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start)
}

private val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph } as NavGraph

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultStogramPopEnterTransition(): EnterTransition {
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.End)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultStogramPopExitTransition(): ExitTransition {
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.End)
}