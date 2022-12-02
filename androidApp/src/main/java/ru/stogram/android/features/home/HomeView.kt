package ru.stogram.android.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.android.R
import ru.stogram.android.components.PostItemView
import ru.stogram.android.components.StoriesView
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun Home() {
    val vm: HomeViewModel = hiltViewModel()
    HomeView(viewModel = vm)
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun HomeView(viewModel: HomeViewModel) {
    val homeState by viewModel.homeState.collectAsState(initial = viewModel.emptyResult())
    HomeView(
        homeState = homeState,
        isRefreshing = viewModel.refreshing,
        onPostAvatarClicked = viewModel::onPostAvatarClicked,
        onPostCommentsClicked = viewModel::onPostCommentsClicked,
        onSwipeRefresh = viewModel::onSwipeRefresh
    )
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
internal fun HomeView(
    homeState: SResult<HomeState>,
    isRefreshing: Boolean = false,
    onPostAvatarClicked: (PostEntity) -> Unit,
    onPostCommentsClicked: (PostEntity) -> Unit,
    onSwipeRefresh: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { onSwipeRefresh() })

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) {

        Box(modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState)) {
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
                        PostItemView(
                            post = post,
                            onPostCommentsClicked = onPostCommentsClicked,
                            onPostAvatarClicked = onPostAvatarClicked
                        )
                        Divider(
                            color = colorResource(id = R.color.color_light_gray),
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(start = 8.dp)
                        )
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

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview(name = "HomePreview", showBackground = true)
@Composable
fun HomePreview(
    @PreviewParameter(HomePreviewParameterProvider::class, limit = 1) result: SResult<HomeState>
) {
    HomeView(
        homeState = result,
        isRefreshing = false,
        onPostAvatarClicked = {},
        onPostCommentsClicked = {},
        onSwipeRefresh = {}
    )
}