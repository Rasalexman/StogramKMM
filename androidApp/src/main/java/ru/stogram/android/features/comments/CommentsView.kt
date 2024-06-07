package ru.stogram.android.features.comments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rasalexman.sresult.common.extensions.applyIfLoading
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.toSuccessResult
import kotlinx.coroutines.flow.MutableStateFlow
import ru.stogram.android.R
import ru.stogram.android.components.InputTextView
import ru.stogram.android.components.SimpleLinearProgressIndicator
import ru.stogram.android.constants.CommentsResult
import ru.stogram.android.mappers.CommentItemUIMapper
import ru.stogram.android.mappers.ICommentItemUIMapper
import ru.stogram.android.models.CommentItemUI
import ru.stogram.models.CommentEntity
import ru.stogram.models.IUser

@Composable
fun CommentsView() {
    CommentsView(viewModel = hiltViewModel())
}

@Composable
fun CommentsView(
    viewModel: CommentViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_comments))
                },
                navigationIcon = {
                    IconButton(onClick = viewModel::onBackClicked) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp
            )
        }
    ) { paddings ->

        val commentsState by viewModel.commentsState.collectAsState()
        CommentsView(
            inputComment = viewModel.inputComment,
            commentsState = commentsState,
            paddingValues = paddings,
            onAvatarClicked = viewModel::onAvatarClicked,
            onLikeClicked = viewModel::onLikeClicked,
            onDoneCommentHandler = viewModel::onDoneCommentHandler
        )
    }
}

@Composable
fun CommentsView(
    inputComment: MutableStateFlow<String>,
    commentsState: CommentsResult,
    paddingValues: PaddingValues,
    onAvatarClicked: (IUser) -> Unit,
    onLikeClicked: (CommentItemUI) -> Unit,
    onDoneCommentHandler: () -> Unit
) {

    val topPaddings = paddingValues.calculateTopPadding()
    val bottomPaddings = topPaddings - 8.dp

    commentsState.applyIfSuccess { items ->
        CommentsItemsView(
            inputComment = inputComment,
            items = items,
            topPaddings = topPaddings,
            bottomPaddings = bottomPaddings,
            onAvatarClicked = onAvatarClicked,
            onLikeClicked = onLikeClicked,
            onDoneCommentHandler = onDoneCommentHandler
        )

    }.applyIfLoading {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = topPaddings)
        ) {
            SimpleLinearProgressIndicator()
        }
    }


}

@Composable
fun CommentsItemsView(
    inputComment: MutableStateFlow<String>,
    topPaddings: Dp,
    bottomPaddings: Dp,
    items: List<CommentItemUI>,
    onAvatarClicked: (IUser) -> Unit,
    onLikeClicked: (CommentItemUI) -> Unit,
    onDoneCommentHandler: () -> Unit
) {

    val comment = remember { inputComment }
    val focusState = remember { mutableStateOf(false) }
    var bottomColumnPadding by remember { mutableStateOf(56.dp) }
    val density = LocalDensity.current
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (focusState.value) {
                    focusManager.clearFocus()
                }
                return Offset.Zero
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(nestedScrollConnection)
        .padding(top = topPaddings, bottom = bottomPaddings)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomColumnPadding)
        ) {
            items(items = items, key = { it.id }) { comment ->
                CommentItemView(
                    comment = comment,
                    onAvatarClicked = onAvatarClicked,
                    onLikeClicked = onLikeClicked
                )

                TabRowDefaults.Divider(
                    color = colorResource(id = R.color.color_light_gray),
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
        }

        InputTextView(
            textFlow = comment,
            focusState = focusState,
            hintResId = R.string.hint_enter_comment,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .onSizeChanged {
                    bottomColumnPadding = density.run {
                        it.height.toDp()
                    }
                },
            imeAction = ImeAction.Done,
            onDoneHandler = {
                focusManager.clearFocus()
                onDoneCommentHandler()
            }
        )

    }
}

class CommentsPreviewParameterProvider : PreviewParameterProvider<CommentsResult> {
    private val commentsMapper: ICommentItemUIMapper = CommentItemUIMapper()
    override val values = sequenceOf(
        CommentEntity.createRandomList("231")
            .map { commentsMapper.convertSingle(it) }.toSuccessResult()
    )
}

@Preview(showBackground = true)
@Composable
fun CommentsPreview(
    @PreviewParameter(CommentsPreviewParameterProvider::class, limit = 1) result: CommentsResult
) {
    val textState = remember { MutableStateFlow("") }
    CommentsItemsView(
        inputComment = textState,
        items = result.data.orEmpty(),
        topPaddings = 0.dp,
        bottomPaddings = 0.dp,
        onAvatarClicked = {},
        onLikeClicked = {  },
        onDoneCommentHandler = {}
    )
}