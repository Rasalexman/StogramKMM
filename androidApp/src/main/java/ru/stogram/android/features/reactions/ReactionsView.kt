package ru.stogram.android.features.reactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ui.Scaffold
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.isLoading
import com.rasalexman.sresult.common.extensions.toSuccessResult
import ru.stogram.android.R
import ru.stogram.android.common.bodyWidth
import ru.stogram.android.common.rememberStateWithLifecycle
import ru.stogram.android.components.TopCircleProgressView
import ru.stogram.android.constants.ReactionsResult
import ru.stogram.models.ReactionEntity

@ExperimentalMaterialApi
@Composable
fun Reactions() {
    val vm: ReactionsViewModel = hiltViewModel()
    ReactionsView(viewModel = vm)
}

@ExperimentalMaterialApi
@Composable
fun ReactionsView(viewModel: ReactionsViewModel) {

    val reactionsState by rememberStateWithLifecycle(stateFlow = viewModel.reactionsState)

    ReactionsView(
        reactionsState = reactionsState,
        isRefreshing = viewModel.refreshing,
        onReactionAvatarClicked = viewModel::onReactionAvatarClicked,
        onSwipeRefresh = viewModel::onSwipeRefresh
    )
}

@ExperimentalMaterialApi
@Composable
internal fun ReactionsView(
    reactionsState: ReactionsResult,
    isRefreshing: Boolean = false,
    onReactionAvatarClicked: (ReactionEntity) -> Unit,
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
                    modifier = Modifier.bodyWidth(),
                ) {
                    reactionsState.applyIfSuccess { items ->
                        items(items = items, key = { it.id }) { reaction ->
                            ReactionItemView(
                                reaction = reaction,
                                onAvatarClicked = onReactionAvatarClicked
                            )

                            TabRowDefaults.Divider(
                                color = colorResource(id = R.color.color_light_gray),
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                            )
                        }
                    }
                }


            if (reactionsState.isLoading) {
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

@ExperimentalMaterialApi
@Preview
@Composable
fun ReactionsPreview(
    @PreviewParameter(ReactionsPreviewParameterProvider::class, limit = 1) result: ReactionsResult
) {
    ReactionsView(
        reactionsState = result,
        isRefreshing = false,
        onReactionAvatarClicked = {},
        onSwipeRefresh = {}
    )
}