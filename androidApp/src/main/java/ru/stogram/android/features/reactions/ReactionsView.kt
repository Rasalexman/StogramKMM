package ru.stogram.android.features.reactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TabRowDefaults
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
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.isLoading
import com.rasalexman.sresult.common.extensions.toSuccessResult
import ru.stogram.android.R
import ru.stogram.android.common.bodyWidth
import ru.stogram.android.components.TopCircleProgressView
import ru.stogram.android.constants.ReactionsResult
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.mappers.IReactionItemUIMapper
import ru.stogram.android.mappers.PostItemUIMapper
import ru.stogram.android.mappers.ReactionItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.IUser
import ru.stogram.models.ReactionEntity

@ExperimentalMaterialApi
@Composable
fun Reactions() {
    ReactionsView(viewModel = hiltViewModel())
}

@ExperimentalMaterialApi
@Composable
internal fun ReactionsView(
    viewModel: ReactionsViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val pullRefreshState = rememberPullRefreshState(viewModel.refreshing, { viewModel.onSwipeRefresh() })

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)) {

            val reactionsState by viewModel.reactionsState.collectAsState()

            ReactionResultView(
                reactionsState = reactionsState,
                onAvatarClicked = viewModel::onAvatarClicked,
                onPostClicked = viewModel::onPostClicked
            )

            if (reactionsState.isLoading) {
                TopCircleProgressView()
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ReactionResultView(
    reactionsState: ReactionsResult,
    onAvatarClicked: (IUser) -> Unit,
    onPostClicked: (PostItemUI) -> Unit
) {
    LazyColumn(
        modifier = Modifier.bodyWidth(),
    ) {
        reactionsState.applyIfSuccess { items ->
            items(items = items, key = { it.id }) { reaction ->
                ReactionItemView(
                    reaction = reaction,
                    onAvatarClicked = onAvatarClicked,
                    onPostClicked = onPostClicked
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
}

class ReactionsPreviewParameterProvider : PreviewParameterProvider<ReactionsResult> {
    private val postItemUIMapper: IPostItemUIMapper = PostItemUIMapper()
    private val reactionItemUIMapper: IReactionItemUIMapper = ReactionItemUIMapper(postItemUIMapper)
    override val values = sequenceOf(
        ReactionEntity.createRandomList()
            .map { reactionItemUIMapper.convertSingle(it) }.toSuccessResult()
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ReactionsPreview(
    @PreviewParameter(ReactionsPreviewParameterProvider::class, limit = 1) result: ReactionsResult
) {
    ReactionResultView(
        reactionsState = result,
        onAvatarClicked = {},
        onPostClicked = {},
    )
}