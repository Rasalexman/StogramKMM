package ru.stogram.android.features.reactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults
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
import com.rasalexman.sresult.common.extensions.isLoading
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.android.R
import ru.stogram.android.common.bodyWidth
import ru.stogram.android.common.rememberStateWithLifecycle
import ru.stogram.android.components.TopCircleProgressView
import ru.stogram.android.constants.ReactionsResult
import ru.stogram.models.ReactionEntity

@Composable
fun Reactions() {
    val vm: ReactionsViewModel by immutableInstance()
    ReactionsView(viewModel = vm)
}

@Composable
fun ReactionsView(viewModel: ReactionsViewModel) {

    val reactionsState by rememberStateWithLifecycle(stateFlow = viewModel.reactionsState)

    ReactionsView(
        viewModel = viewModel,
        reactionsState = reactionsState,
        refresh = viewModel::onSwipeRefresh
    )
}

@Composable
internal fun ReactionsView(
    viewModel: ReactionsViewModel,
    reactionsState: ReactionsResult,
    refresh: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {

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
                    modifier = Modifier.bodyWidth(),
                ) {
                    reactionsState.applyIfSuccess { items ->
                        items(items = items, key = { it.id }) { reaction ->
                            ReactionItemView(reaction = reaction)

                            TabRowDefaults.Divider(
                                color = colorResource(id = R.color.color_light_gray),
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                            )
                        }
                    }
                }
            }

            if(reactionsState.isLoading) {
                TopCircleProgressView()
            }
        }
    }
}

class ReactionsPreviewParameterProvider : PreviewParameterProvider<ReactionsResult> {
    override val values = sequenceOf(
        ReactionEntity.createRandomList().toSuccessResult()
    )
}

@Preview
@Composable
fun ReactionsPreview(
    @PreviewParameter(ReactionsPreviewParameterProvider::class, limit = 1) result: ReactionsResult
) {
    ReactionsView(viewModel = ReactionsViewModel(), reactionsState = result) {

    }
}