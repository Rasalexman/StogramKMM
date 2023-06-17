package ru.stogram.android.features.postdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ui.Scaffold
import androidx.compose.foundation.ExperimentalFoundationApi
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import ru.stogram.android.R
import ru.stogram.android.common.orZero
import ru.stogram.android.components.*
import ru.stogram.android.constants.CommentsResult
import ru.stogram.android.constants.PostDetailsResult
import ru.stogram.android.features.comments.CommentItemView
import ru.stogram.android.features.comments.CommentViewModel
import ru.stogram.android.models.CommentItemUI
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.IUser

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostDetailsView() {
    val postDetailsViewModel: PostDetailsViewModel = hiltViewModel()
    val commentsViewModel: CommentViewModel = hiltViewModel()
    PostDetailsView(postDetailsViewModel, commentsViewModel)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostDetailsView(
    postDetailsViewModel: PostDetailsViewModel,
    commentsViewModel: CommentViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val postDetailsState by postDetailsViewModel.postState.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    postDetailsState.applyIfSuccess { post ->
                        if(!post.user.isCurrentUser) {
                            AvatarNameDescView(
                                user = post.user,
                                size = 32.dp,
                                onClick = {
                                    postDetailsViewModel.onToolBarAvatarClicked(post.user)
                                }
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = postDetailsViewModel::onBackClicked) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp
            )

        }
    ) { paddings ->
        val commentsState by commentsViewModel.commentsState.collectAsState()

        PostDetailsView(
            inputComment = commentsViewModel.inputComment,
            postDetailsState = postDetailsState,
            commentsState = commentsState,
            paddingValues = paddings,
            onAvatarClicked = postDetailsViewModel::onAvatarClicked,
            onPostLikeClicked = postDetailsViewModel::onPostLikeClicked,
            onCommentLikeClicked = commentsViewModel::onLikeClicked,
            onDoneCommentHandler = commentsViewModel::onDoneCommentHandler
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostDetailsView(
    inputComment: MutableStateFlow<String>,
    postDetailsState: PostDetailsResult,
    commentsState: CommentsResult,
    paddingValues: PaddingValues,
    onAvatarClicked: (IUser) -> Unit,
    onPostLikeClicked: (PostItemUI) -> Unit,
    onCommentLikeClicked: (CommentItemUI) -> Unit,
    onDoneCommentHandler: () -> Unit
) {

    val topPaddings = paddingValues.calculateTopPadding()
    if(commentsState.isLoading() || postDetailsState.isLoading()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = topPaddings)
        ) {
            SimpleLinearProgressIndicator()
        }
    }

    postDetailsState.applyIfSuccess { post ->
        val postPhotos: List<String> = post.postContent
        val bottomPaddings = topPaddings - 8.dp

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
                .padding(bottom = bottomColumnPadding)) {

                item { PostContentView(postPhotos) }

                item {
                    Row(modifier = Modifier.padding(all = 8.dp)) {
                        LikesView(count = post.likesCount.orZero(), isSelected = post.isLiked) {
                            onPostLikeClicked.invoke(post)
                        }
                    }
                }

                item {
                    Text(
                        text = post.text,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 14.sp
                    )
                }

                item {
                    Text(
                        text = stringResource(id = R.string.post_comments),
                        modifier = Modifier.padding(all = 8.dp),
                        fontSize = 18.sp
                    )
                }

                commentsState.applyIfSuccess { items ->
                    items(items = items, key = { it.id }) { comment ->
                        CommentItemView(
                            comment = comment,
                            onAvatarClicked = onAvatarClicked,
                            onLikeClicked = onCommentLikeClicked
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
}