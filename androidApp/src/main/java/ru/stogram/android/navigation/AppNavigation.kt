package ru.stogram.android.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.navigation
import com.rasalexman.kodi.core.immutableInstance
import ru.stogram.android.constants.ScreenNames
import ru.stogram.android.features.create.Create
import ru.stogram.android.features.home.Home
import ru.stogram.android.features.home.HomeView
import ru.stogram.android.features.home.HomeViewModel
import ru.stogram.android.features.profile.Profile
import ru.stogram.android.features.reactions.Reactions
import ru.stogram.android.features.reactions.ReactionsView
import ru.stogram.android.features.reactions.ReactionsViewModel
import ru.stogram.android.features.search.Search
import ru.stogram.android.features.search.SearchView
import ru.stogram.android.features.search.SearchViewModel

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
    object Create : LeafScreen(ScreenNames.CREATE)
    object Reactions : LeafScreen(ScreenNames.REACTIONS)
    object Profile : LeafScreen(ScreenNames.PROFILE)
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
        addCreateTopLevel(navController)
        addReactionsTopLevel(navController)
        addProfileTopLevel(navController)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addHomeTopLevel(
    navController: NavController
) {
    val route = LeafScreen.Home.createRoute(Screen.Home)
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Home.createRoute(Screen.Home),
    ) {
        addBottomNavigationView(
            route = route,
            label = ScreenNames.HOME
        ) {
            Home()
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addSearchTopLevel(
    navController: NavController
) {
    val route = LeafScreen.Search.createRoute(Screen.Search)
    navigation(
        route = Screen.Search.route,
        startDestination = route,
    ) {
        addBottomNavigationView(
            route = route,
            label = ScreenNames.SEARCH
        ) {
            Search()
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCreateTopLevel(
    navController: NavController
) {
    val route = LeafScreen.Create.createRoute(Screen.Create)
    navigation(
        route = Screen.Create.route,
        startDestination = route,
    ) {
        addBottomNavigationView(
            route = route,
            label = ScreenNames.CREATE
        ) {
            Create()
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addReactionsTopLevel(
    navController: NavController
) {
    val route = LeafScreen.Reactions.createRoute(Screen.Reactions)
    navigation(
        route = Screen.Reactions.route,
        startDestination = route,
    ) {
        addBottomNavigationView(
            route = route,
            label = ScreenNames.REACTIONS
        ) {
            Reactions()
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addProfileTopLevel(
    navController: NavController
) {
    val route = LeafScreen.Profile.createRoute(Screen.Profile)
    navigation(
        route = Screen.Profile.route,
        startDestination = route,
    ) {
        addBottomNavigationView(
            route = route,
            label = ScreenNames.PROFILE
        ) {
            Profile()
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addBottomNavigationView(
    route: String,
    label: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        debugLabel = label,
        content = content
    )
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