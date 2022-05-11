package ru.stogram.android.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.toSuccessResult
import ru.stogram.android.common.bodyWidth
import ru.stogram.android.common.rememberStateWithLifecycle
import ru.stogram.android.components.PhotoImageView
import ru.stogram.android.components.PostContentView
import ru.stogram.android.components.SearchBarView
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.features.home.HomePreviewParameterProvider
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

@ExperimentalPagerApi
@Composable
fun Search() {
    val vm: SearchViewModel by immutableInstance()
    SearchView(viewModel = vm)
}

@ExperimentalPagerApi
@Composable
fun SearchView(viewModel: SearchViewModel) {
    val postsState by rememberStateWithLifecycle(stateFlow = viewModel.postsState)
    SearchView(
        viewModel = viewModel,
        postsState = postsState,
        refresh = viewModel::onSwipeRefresh
    )
}

@ExperimentalPagerApi
@Composable
internal fun SearchView(
    viewModel: SearchViewModel,
    postsState: PostsResult,
    refresh: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(viewModel.refreshing),
            onRefresh = refresh,
            indicatorPadding = paddingValues,
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                SearchBarView(textState)

                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    postsState.applyIfSuccess { posts ->
                        items(posts) { post ->
                            PhotoImageView(url = post.firstPhoto)
                        }
                    }
                }
            }
        }
    }
}

class SearchPreviewParameterProvider : PreviewParameterProvider<PostsResult> {
    override val values = sequenceOf(
        PostEntity.createRandomList().toSuccessResult()
    )
}

@ExperimentalPagerApi
@Preview
@Composable
fun SearchPreview(
    @PreviewParameter(SearchPreviewParameterProvider::class, limit = 1) result: PostsResult
) {
    SearchView(viewModel = SearchViewModel(), postsState = result) {

    }
}