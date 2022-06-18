package ru.stogram.android.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sresult.common.extensions.*
import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.android.R
import ru.stogram.android.components.PostItemView
import ru.stogram.android.components.StoriesView
import ru.stogram.android.components.TopCircleProgressView
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

@ExperimentalPagerApi
@Composable
fun Home(navController: NavController) {
    val vm: HomeViewModel by immutableInstance()
    HomeView(viewModel = vm)
}

@ExperimentalPagerApi
@Composable
fun HomeView(viewModel: HomeViewModel) {
    val homeState by viewModel.homeState.collectAsState(initial = viewModel.emptyResult())
    HomeView(
        homeState = homeState,
        viewModel = viewModel,
        refresh = viewModel::onSwipeRefresh
    )
}

@ExperimentalPagerApi
@Composable
internal fun HomeView(
    homeState: SResult<HomeState>,
    viewModel: HomeViewModel,
    refresh: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(viewModel.refreshing),
                onRefresh = refresh,
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    homeState.applyIfSuccess { state ->
                        item {
                            StoriesView(stories = state.stories) { user ->
                                logg { "----> stories user name = ${user.name}" }
                            }
                        }
                        item {
                            Divider(
                                color = colorResource(id = R.color.color_light_gray),
                                thickness = 1.dp
                            )
                        }

                        items(
                            items = state.posts,
                            key = { it.id }
                        ) { post ->
                            PostItemView(post = post, viewModel = viewModel)
                            Divider(
                                color = colorResource(id = R.color.color_light_gray),
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                            )
                        }
                    }
                }
            }

//            if (homeState.isLoading) {
//                TopCircleProgressView()
//            }
        }
    }
}

class HomePreviewParameterProvider : PreviewParameterProvider<SResult<HomeState>> {
    override val values = sequenceOf(
        HomeState(
            PostEntity.createRandomList(),
            UserEntity.createRandomList(true)
        ).toSuccessResult()
    )
}

@ExperimentalPagerApi
@Preview(name = "HomePreview", showBackground = true)
@Composable
fun HomePreview(
    @PreviewParameter(HomePreviewParameterProvider::class, limit = 1) result: SResult<HomeState>
) {
    HomeView(homeState = result, viewModel = HomeViewModel(), refresh = {})
}