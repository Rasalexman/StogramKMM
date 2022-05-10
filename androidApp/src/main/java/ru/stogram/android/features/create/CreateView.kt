package ru.stogram.android.features.create

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rasalexman.kodi.core.immutableInstance
import ru.stogram.android.common.Layout
import ru.stogram.android.common.bodyWidth

@Composable
fun Create() {
    val vm: CreateViewModel by immutableInstance()
    CreateView(viewModel = vm)
}

@Composable
fun CreateView(viewModel: CreateViewModel) {
    CreateView(
        viewModel = viewModel,
        refresh = viewModel::onSwipeRefresh
    )
}

@Composable
internal fun CreateView(
    viewModel: CreateViewModel,
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
                item {
                    Text(text = "This is CreateView()")
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchPreview() {
    CreateView(viewModel = CreateViewModel())
}