package ru.stogram.android.features.home

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.android.R
import ru.stogram.android.common.bodyWidth
import ru.stogram.android.common.rememberStateWithLifecycle
import ru.stogram.android.components.PostView
import ru.stogram.android.components.StoriesView
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.constants.StoriesResult
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

@ExperimentalPagerApi
@Composable
fun Home() {
    val vm: HomeViewModel by immutableInstance()
    HomeView(viewModel = vm)
}

@ExperimentalPagerApi
@Composable
fun HomeView(viewModel: HomeViewModel) {

    // by rememberStateWithLifecycle(stateFlow = viewModel.storiesState)
    val storiesState by viewModel.storiesState.collectAsState(initial = StoriesResult.emptyResult())
    val postsState by viewModel.postsState.collectAsState(initial = PostsResult.emptyResult())

    HomeView(
        storiesState = storiesState,
        postsState = postsState,
        viewModel = viewModel,
        refresh = viewModel::onSwipeRefresh
    )
}

@ExperimentalPagerApi
@Composable
internal fun HomeView(
    storiesState: StoriesResult,
    postsState: PostsResult,
    viewModel: HomeViewModel,
    refresh: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) {

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
                storiesState.applyIfSuccess { stories ->
                    item {
                        StoriesView(stories = stories) { user ->
                            logg { "----> stories user name = ${user.name}" }
                        }
                    }
                    item {
                        Divider(color = colorResource(id = R.color.color_light_gray), thickness = 1.dp)
                    }
                }

                postsState.applyIfSuccess { items ->
                    items(
                        items = items,
                        key = { it.id }
                    ) { post ->
                        PostView(post = post)
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
    }
}

class HomePreviewParameterProvider : PreviewParameterProvider<Pair<StoriesResult, PostsResult>> {
    override val values = sequenceOf(
        UserEntity.createRandomList(true).toSuccessResult() to PostEntity.createRandomList().toSuccessResult()
    )
}

@ExperimentalPagerApi
@Preview(name = "HomePreview", showBackground = true)
@Composable
fun HomePreview(
    @PreviewParameter(HomePreviewParameterProvider::class, limit = 1) result: Pair<StoriesResult, PostsResult>
) {
    HomeView(storiesState = result.first, postsState = result.second, viewModel = HomeViewModel(), refresh = {})
}