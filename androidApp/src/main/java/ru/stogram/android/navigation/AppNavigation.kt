package ru.stogram.android.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.navigation
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.constants.ScreenNames
import ru.stogram.android.features.create.Create
import ru.stogram.android.features.home.Home
import ru.stogram.android.features.profile.Profile
import ru.stogram.android.features.reactions.Reactions
import ru.stogram.android.features.search.Search

internal sealed class Screen(val route: String) {
    object Login : Screen(ScreenNames.Login)
    object Register : Screen(ScreenNames.Register)

    object Main : Screen(ScreenNames.Main)

    object Home : Screen(ScreenNames.HOME)
    object Search : Screen(ScreenNames.SEARCH)
    object Create : Screen(ScreenNames.CREATE)
    object Reactions : Screen(ScreenNames.REACTIONS)
    object Profile : Screen(ScreenNames.PROFILE)

    object UserProfile : Screen(ScreenNames.USER_PROFILE)
    object PostDetails : Screen(ScreenNames.POST_DETAILS)
    object PostComments : Screen(ScreenNames.POST_COMMENTS)
    object SubsAndObservers : Screen(ScreenNames.SUBS_OBSERVERS)
}

private sealed class LeafScreen(
    private val route: String
) {
    fun createRoute(root: Screen) = "${root.route}/$route"
    object MenuProfile : LeafScreen(ScreenNames.USER_PROFILE)
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    val builder: NavGraphBuilder.() -> Unit = {
        addHomeTopLevel()
        addSearchTopLevel()
        addCreateTopLevel()
        addReactionsTopLevel()
        addProfileTopLevel()
    }

    androidx.navigation.compose.NavHost(
        navController = navController,
        graph = remember(Screen.Home.route, builder) {
            navController.createGraph(Screen.Home.route, null, builder)
        },
        modifier = modifier,
    )
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addHomeTopLevel() {
    addBottomNavigationView(
        route = Screen.Home.route,
        label = ScreenNames.HOME
    ) {
        Home()
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
private fun NavGraphBuilder.addSearchTopLevel() {
    addBottomNavigationView(
        route = Screen.Search.route,
        label = ScreenNames.SEARCH
    ) {
        Search()
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCreateTopLevel() {
    addBottomNavigationView(
        route = Screen.Create.route,
        label = ScreenNames.CREATE
    ) {
        Create()
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
private fun NavGraphBuilder.addReactionsTopLevel() {
    addBottomNavigationView(
        route = Screen.Reactions.route,
        label = ScreenNames.REACTIONS
    ) {
        Reactions()
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addProfileTopLevel() {
    val route = LeafScreen.MenuProfile.createRoute(Screen.Profile)
    navigation(
        route = Screen.Profile.route,
        startDestination = route,
    ) {
        addBottomNavigationView(
            route = route,
            label = ScreenNames.PROFILE,
            argNames = listOf(navArgument(ArgsNames.USER_ID) {
                defaultValue = ""
                nullable = true
                type = NavType.StringType
            }, navArgument(ArgsNames.USER_LOGIN) {
                defaultValue = ""
                nullable = true
                type = NavType.StringType
            })
        ) {
            Profile()
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
    composableRoute(
        route = route,
        arguments = argNames,
        debugLabel = label,
        content = content
    )
}

//private val NavDestination.hostNavGraph: NavGraph
//    get() = hierarchy.first { it is NavGraph } as NavGraph

//@ExperimentalAnimationApi
//private fun AnimatedContentScope<*>.defaultStogramEnterTransition(
//    initial: NavBackStackEntry,
//    target: NavBackStackEntry,
//): EnterTransition {
//    val initialNavGraph = initial.destination.hostNavGraph
//    val targetNavGraph = target.destination.hostNavGraph
//    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
//    if (initialNavGraph.id != targetNavGraph.id) {
//        return fadeIn()
//    }
//    // Otherwise we're in the same nav graph, we can imply a direction
//    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Start)
//}
//
//@ExperimentalAnimationApi
//private fun AnimatedContentScope<*>.defaultStogramExitTransition(
//    initial: NavBackStackEntry,
//    target: NavBackStackEntry,
//): ExitTransition {
//    val initialNavGraph = initial.destination.hostNavGraph
//    val targetNavGraph = target.destination.hostNavGraph
//    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
//    if (initialNavGraph.id != targetNavGraph.id) {
//        return fadeOut()
//    }
//    // Otherwise we're in the same nav graph, we can imply a direction
//    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start)
//}

//@ExperimentalAnimationApi
//private fun AnimatedContentScope<*>.defaultStogramPopEnterTransition(): EnterTransition {
//    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.End)
//}
//
//@ExperimentalAnimationApi
//private fun AnimatedContentScope<*>.defaultStogramPopExitTransition(): ExitTransition {
//    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.End)
//}