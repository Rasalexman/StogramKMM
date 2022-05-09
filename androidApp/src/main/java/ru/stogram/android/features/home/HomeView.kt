package ru.stogram.android.features.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.android.common.Layout
import ru.stogram.android.common.bodyWidth
import ru.stogram.android.common.rememberStateWithLifecycle
import ru.stogram.models.PostEntity

@Composable
fun HomeView(viewModel: HomeViewModel) {

    val viewState by rememberStateWithLifecycle(stateFlow = viewModel.resultState)

    HomeView(
        viewState = viewState,
        viewModel = viewModel,
        refresh = viewModel::onSwipeRefresh
    )
}

@Composable
internal fun HomeView(
    viewState: SResult<List<PostEntity>>,
    viewModel: HomeViewModel,
    refresh: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

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
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.bodyWidth(),
            ) {
                item {
                    Spacer(Modifier.height(Layout.gutter))
                }

                viewState.applyIfSuccess { items ->
                    items(
                        items = items,
                        key = { it.id }
                    ) { post ->
                        Text(text = post.userName.orEmpty())
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    HomeView(viewModel = HomeViewModel())
}