package ru.stogram.android.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.logg
import ru.stogram.android.components.PostImageView
import ru.stogram.android.components.SearchBarView
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.mappers.PostItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.PostEntity

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun Search() {
    SearchView(viewModel = hiltViewModel())
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
internal fun SearchView(
    viewModel: SearchViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val textState = remember { viewModel.searchQuery }
    val focusManager = LocalFocusManager.current
    val focusState = remember { mutableStateOf(false) }
    val isRefreshing = viewModel.refreshing
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.onSwipeRefresh() })

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (focusState.value) {
                    logg { "----> onPreScroll hide keyboard" }
                    focusManager.clearFocus()
                }
                return Offset.Zero
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                // attach as a parent to the nested scroll system
                .nestedScroll(nestedScrollConnection)
                .pullRefresh(pullRefreshState)
        ) {
            SearchBarView(textState, focusState) {
                focusManager.clearFocus()
                viewModel.onSearchButtonPressed()
            }

            val postsState by viewModel.postsState.collectAsState()
            postsState.applyIfSuccess { posts ->
                SearchResultView(posts, viewModel::onPostClicked)
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun SearchResultView(
    posts: List<PostItemUI>,
    onPostClicked: (PostItemUI) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        items(items = posts, key = { it.id }) { post ->
            PostImageView(post = post, onClick = onPostClicked)
        }
    }
}

class SearchPreviewParameterProvider : PreviewParameterProvider<List<PostItemUI>> {
    private val postItemUIMapper: IPostItemUIMapper = PostItemUIMapper()
    override val values = sequenceOf(
        PostEntity.createRandomList().map { postItemUIMapper.convertSingle(it) }
    )
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview
@Composable
fun SearchPreview(
    @PreviewParameter(SearchPreviewParameterProvider::class, limit = 1) result: List<PostItemUI>
) {
    SearchResultView(posts = result) {}
}