package ru.stogram.android.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
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
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.isLoading
import com.rasalexman.sresult.common.extensions.logg
import ru.stogram.android.R
import ru.stogram.android.components.PostItemView
import ru.stogram.android.components.StoriesView
import ru.stogram.android.components.TopCircleProgressView
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.mappers.PostItemUIMapper
import ru.stogram.android.mappers.UserUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home() {
    HomeView(viewModel = hiltViewModel())
}

@ExperimentalMaterialApi
@Composable
fun HomeView(viewModel: HomeViewModel) {
    HomeView(
        viewModel = viewModel,
        onPostAvatarClicked = viewModel::onPostAvatarClicked,
        onPostCommentsClicked = viewModel::onPostCommentsClicked,
        onLikeClicked = viewModel::onPostLikeClicked,
        onSwipeRefresh = viewModel::onSwipeRefresh
    )
}

@ExperimentalMaterialApi
@Composable
internal fun HomeView(
    viewModel: HomeViewModel,
    onPostAvatarClicked: (PostItemUI) -> Unit,
    onPostCommentsClicked: (PostItemUI) -> Unit,
    onLikeClicked: (PostItemUI) -> Unit,
    onSwipeRefresh: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) {

        val pullRefreshState = rememberPullRefreshState(viewModel.refreshing, { onSwipeRefresh() })

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {

            val homeState by viewModel.homeState.collectAsState()

            homeState.applyIfSuccess { state ->
                HomeView(
                    stories = state.stories,
                    posts = state.posts,
                    onPostAvatarClicked = onPostAvatarClicked,
                    onPostCommentsClicked = onPostCommentsClicked,
                    onLikeClicked = onLikeClicked
                )
            }


            if (homeState.isLoading) {
                TopCircleProgressView()
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HomeView(
    stories: List<UserEntity>,
    posts: List<PostItemUI>,
    onPostAvatarClicked: (PostItemUI) -> Unit,
    onPostCommentsClicked: (PostItemUI) -> Unit,
    onLikeClicked: (PostItemUI) -> Unit
) {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            StoriesView(stories = stories) { user ->
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
            items = posts,
            key = { it.id }
        ) { post ->
            PostItemView(
                post = post,
                onCommentsClicked = onPostCommentsClicked,
                onAvatarClicked = onPostAvatarClicked,
                onLikeClicked = onLikeClicked
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

class HomePreviewParameterProvider : PreviewParameterProvider<HomeState> {
    private val postItemUIMapper: IPostItemUIMapper = PostItemUIMapper(UserUIMapper())
    override val values = sequenceOf(
        HomeState(
            PostEntity.createRandomList().map { postItemUIMapper.convertSingle(it) },
            UserEntity.createRandomList(true)
        )
    )
}

@ExperimentalMaterialApi
@Preview(name = "HomePreview", showBackground = true)
@Composable
fun HomePreview(
    @PreviewParameter(HomePreviewParameterProvider::class, limit = 1) result: HomeState
) {
    HomeView(
        stories = result.stories,
        posts = result.posts,
        onPostAvatarClicked = {},
        onPostCommentsClicked = {},
        onLikeClicked = {}
    )
}