package ru.stogram.android.features.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.android.R
import ru.stogram.android.common.bodyWidth
import ru.stogram.android.common.rememberStateWithLifecycle
import ru.stogram.android.components.PostView
import ru.stogram.android.constants.PostsResult
import ru.stogram.models.PostEntity

@Composable
fun Home() {
    val vm: HomeViewModel by immutableInstance()
    HomeView(viewModel = vm)
}

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
                viewState.applyIfSuccess { items ->
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

class HomePreviewParameterProvider : PreviewParameterProvider<PostsResult> {
    override val values = sequenceOf(
        PostEntity.createRandom().toSuccessResult()
    )
}

@Preview(name = "HomePreview", showBackground = true)
@Composable
fun HomePreview(
    @PreviewParameter(HomePreviewParameterProvider::class, limit = 1) result: PostsResult
) {
    HomeView(viewState = result, viewModel = HomeViewModel(), refresh = {})
}