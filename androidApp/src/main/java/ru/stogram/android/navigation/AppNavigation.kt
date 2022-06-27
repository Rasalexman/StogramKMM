package ru.stogram.android.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.constants.ScreenNames
import ru.stogram.android.features.create.Create
import ru.stogram.android.features.home.Home
import ru.stogram.android.features.profile.Profile
import ru.stogram.android.features.reactions.Reactions
import ru.stogram.android.features.search.Search
import ru.stogram.models.UserEntity

internal sealed class Screen(val route: String) {
    object Main : Screen(ScreenNames.Main)

    object Home : Screen(ScreenNames.HOME)
    object Search : Screen(ScreenNames.SEARCH)
    object Create : Screen(ScreenNames.CREATE)
    object Reactions : Screen(ScreenNames.REACTIONS)
    object Profile : Screen(ScreenNames.PROFILE)

    object UserProfile : Screen(ScreenNames.USER_PROFILE)
    object PostDetails : Screen(ScreenNames.POST_DETAILS)
    object PostComments : Screen(ScreenNames.POST_COMMENTS)
}

private sealed class LeafScreen(
    private val route: String
) {
    fun createRoute(root: Screen) = "${root.route}/$route"
    object MenuProfile : LeafScreen(ScreenNames.USER_PROFILE)
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route,
//        enterTransition = { defaultStogramEnterTransition(initialState, targetState) },
//        exitTransition = { defaultStogramExitTransition(initialState, targetState) },
//        popEnterTransition = { defaultStogramPopEnterTransition() },
//        popExitTransition = { defaultStogramPopExitTransition() },
        modifier = modifier,
    ) {
        addHomeTopLevel(navController)
        addSearchTopLevel(navController)
        addCreateTopLevel(navController)
        addReactionsTopLevel(navController)
        addProfileTopLevel(navController)
    }
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addHomeTopLevel(
    navController: NavController
) {
    addBottomNavigationView(
        route = Screen.Home.route,
        label = ScreenNames.HOME
    ) {
        Home(navController)
    }
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addSearchTopLevel(
    navController: NavController
) {
    addBottomNavigationView(
        route = Screen.Search.route,
        label = ScreenNames.SEARCH
    ) {
        Search()
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCreateTopLevel(
    navController: NavController
) {
    addBottomNavigationView(
        route = Screen.Create.route,
        label = ScreenNames.CREATE
    ) {
        Create()
    }
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addReactionsTopLevel(
    navController: NavController
) {
    addBottomNavigationView(
        route = Screen.Reactions.route,
        label = ScreenNames.REACTIONS
    ) {
        Reactions()
    }
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addProfileTopLevel(
    navController: NavController
) {
    val route = LeafScreen.MenuProfile.createRoute(Screen.Profile)
    navigation(
        route = Screen.Profile.route,
        startDestination = route,
    ) {
        addBottomNavigationView(
            route = route,
            label = ScreenNames.PROFILE,
            argNames = listOf(navArgument(ArgsNames.USER_ID) {
                defaultValue = UserEntity.DEFAULT_USER_ID
                nullable = true
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val userProfileId = backStackEntry.arguments?.getString(ArgsNames.USER_ID)
            Profile(userProfileId)
        }
    }
}


@ExperimentalAnimationApi
private fun NavGraphBuilder.addBottomNavigationView(
    route: String,
    label: String,
    argNames: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = argNames,
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