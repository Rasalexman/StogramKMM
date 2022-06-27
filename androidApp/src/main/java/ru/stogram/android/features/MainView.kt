package ru.stogram.android.features

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.insets.ui.BottomNavigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rasalexman.kodi.core.*
import ru.stogram.android.navigation.AppNavigation
import ru.stogram.android.navigation.Screen
import ru.stogram.android.navigation.navigateToBottomRouter
import ru.stogram.android.theme.AppBarAlphas

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun MainView() {

    val navController = rememberAnimatedNavController().also {
        kodi {
            unbind<NavController>()
            bind<NavController>() with provider { it }
        }
    }
    val configuration = LocalConfiguration.current
    val useBottomNavigation by remember {
        derivedStateOf { configuration.smallestScreenWidthDp < 600 }
    }

    Scaffold(
        bottomBar = {
            if (useBottomNavigation) {
                val currentSelectedItem by navController.currentScreenAsState()
                MainBottomNavigation(
                    selectedNavigation = currentSelectedItem,
                    onNavigationSelected = { selected ->
                        navController.navigateToBottomRouter(selected.route, true)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Spacer(
                    Modifier
                        .windowInsetsBottomHeight(WindowInsets.navigationBars)
                        .fillMaxWidth()
                )
            }
        }
    ) { innerPadding ->
        Row(Modifier.fillMaxSize().statusBarsPadding().padding(innerPadding)) {
//            if (!useBottomNavigation) {
//                val currentSelectedItem by navController.currentScreenAsState()
//                MainNavigationRail(
//                    selectedNavigation = currentSelectedItem,
//                    onNavigationSelected = { selected ->
//                        navController.navigateToBottomRouter(selected.route, true)
//                    },
//                    modifier = Modifier.fillMaxHeight(),
//                )
//
//                Divider(
//                    Modifier
//                        .fillMaxHeight()
//                        .width(1.dp)
//                )
//            }

            AppNavigation(
                navController = navController,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}

/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Stable
@Composable
private fun NavController.currentScreenAsState(): State<Screen> {
    val selectedItem = remember { mutableStateOf<Screen>(Screen.Home) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == Screen.Home.route } -> {
                    selectedItem.value = Screen.Home
                }
                destination.hierarchy.any { it.route == Screen.Reactions.route } -> {
                    selectedItem.value = Screen.Reactions
                }
                destination.hierarchy.any { it.route == Screen.Create.route } -> {
                    selectedItem.value = Screen.Create
                }
                destination.hierarchy.any { it.route == Screen.Search.route } -> {
                    selectedItem.value = Screen.Search
                }
                destination.hierarchy.any { it.route == Screen.Profile.route } -> {
                    selectedItem.value = Screen.Profile
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}

@Composable
internal fun MainBottomNavigation(
    selectedNavigation: Screen,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = AppBarAlphas.translucentBarAlpha()),
        contentColor = contentColorFor(MaterialTheme.colors.surface),
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        modifier = modifier
    ) {
        MainNavigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    MainNavigationItemIcon(
                        item = item,
                        selected = selectedNavigation == item.screen
                    )
                },
                label = { Text(text = stringResource(item.labelResId)) },
                selected = selectedNavigation == item.screen,
                onClick = { onNavigationSelected(item.screen) },
            )
        }
    }
}


@ExperimentalMaterialApi
@Composable
internal fun MainNavigationRail(
    selectedNavigation: Screen,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colors.surface,
        elevation = NavigationRailDefaults.Elevation,
        modifier = modifier,
    ) {
        NavigationRail(
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.onSurface,
            elevation = 0.dp,
            modifier = Modifier.padding(
                WindowInsets.systemBars
                    .only(WindowInsetsSides.Start + WindowInsetsSides.Vertical)
                    .asPaddingValues()
            )
        ) {
            MainNavigationItems.forEach { item ->
                NavigationRailItem(
                    icon = {
                        MainNavigationItemIcon(
                            item = item,
                            selected = selectedNavigation == item.screen
                        )
                    },
                    alwaysShowLabel = false,
                    label = { Text(text = stringResource(item.labelResId)) },
                    selected = selectedNavigation == item.screen,
                    onClick = { onNavigationSelected(item.screen) },
                )
            }
        }
    }
}

@Composable
private fun MainNavigationItemIcon(item: MainNavigationItem, selected: Boolean) {
    val painter = when (item) {
        is MainNavigationItem.ResourceIcon -> painterResource(item.iconResId)
        is MainNavigationItem.ImageVectorIcon -> rememberVectorPainter(item.iconImageVector)
    }
    val selectedPainter = when (item) {
        is MainNavigationItem.ResourceIcon -> item.selectedIconResId?.let { painterResource(it) }
        is MainNavigationItem.ImageVectorIcon -> item.selectedImageVector?.let { rememberVectorPainter(it) }
    }

    if (selectedPainter != null) {
        Crossfade(targetState = selected) {
            Icon(
                painter = if (it) selectedPainter else painter,
                contentDescription = stringResource(item.contentDescriptionResId),
            )
        }
    } else {
        Icon(
            painter = painter,
            contentDescription = stringResource(item.contentDescriptionResId),
        )
    }
}

private sealed class MainNavigationItem(
    val screen: Screen,
    @StringRes val labelResId: Int,
    @StringRes val contentDescriptionResId: Int,
) {
    class ResourceIcon(
        screen: Screen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        @DrawableRes val iconResId: Int,
        @DrawableRes val selectedIconResId: Int? = null,
    ) : MainNavigationItem(screen, labelResId, contentDescriptionResId)

    class ImageVectorIcon(
        screen: Screen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        val iconImageVector: ImageVector,
        val selectedImageVector: ImageVector? = null,
    ) : MainNavigationItem(screen, labelResId, contentDescriptionResId)
}

private val MainNavigationItems = listOf(
    MainNavigationItem.ImageVectorIcon(
        screen = Screen.Home,
        labelResId = ru.stogram.android.R.string.tab_home,
        contentDescriptionResId = ru.stogram.android.R.string.tab_home,
        iconImageVector = Icons.Outlined.Home,
        selectedImageVector = Icons.Default.Home,
    ),
    MainNavigationItem.ImageVectorIcon(
        screen = Screen.Search,
        labelResId = ru.stogram.android.R.string.tab_search,
        contentDescriptionResId = ru.stogram.android.R.string.tab_search,
        iconImageVector = Icons.Outlined.Search,
        selectedImageVector = Icons.Default.Search,
    ),
    MainNavigationItem.ImageVectorIcon(
        screen = Screen.Create,
        labelResId = ru.stogram.android.R.string.tab_create,
        contentDescriptionResId = ru.stogram.android.R.string.tab_create,
        iconImageVector = Icons.Outlined.Add,
        selectedImageVector = Icons.Default.Add,
    ),
    MainNavigationItem.ImageVectorIcon(
        screen = Screen.Reactions,
        labelResId = ru.stogram.android.R.string.tab_reactions,
        contentDescriptionResId = ru.stogram.android.R.string.tab_reactions,
        iconImageVector = Icons.Outlined.Favorite,
        selectedImageVector = Icons.Default.Favorite,
    ),
    MainNavigationItem.ImageVectorIcon(
        screen = Screen.Profile,
        labelResId = ru.stogram.android.R.string.tab_profile,
        contentDescriptionResId = ru.stogram.android.R.string.tab_profile,
        iconImageVector = Icons.Outlined.Person,
        selectedImageVector = Icons.Default.Person
    ),
)