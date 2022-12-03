package ru.stogram.android.features.postdetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.isLoading
import ru.stogram.android.R
import ru.stogram.android.common.orZero
import ru.stogram.android.components.AvatarNameDescView
import ru.stogram.android.components.LikesView
import ru.stogram.android.components.PostContentView
import ru.stogram.android.components.TopCircleProgressView
import ru.stogram.android.constants.CommentsResult
import ru.stogram.android.constants.PostDetailsResult
import ru.stogram.android.features.comments.CommentItemView
import ru.stogram.android.features.comments.CommentViewModel
import ru.stogram.android.models.CommentItemUI
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.IUser

@ExperimentalPagerApi
@Composable
fun PostDetailsView() {
    val postDetailsViewModel: PostDetailsViewModel = hiltViewModel()
    val commentsViewModel: CommentViewModel = hiltViewModel()
    PostDetailsView(postDetailsViewModel, commentsViewModel)
}

@ExperimentalPagerApi
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
                                onClick = postDetailsViewModel::onBackClicked
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
            postDetailsState = postDetailsState,
            commentsState = commentsState,
            paddingValues = paddings,
            onAvatarClicked = postDetailsViewModel::onAvatarClicked,
            onPostLikeClicked = postDetailsViewModel::onPostLikeClicked,
            onCommentLikeClicked = commentsViewModel::onLikeClicked
        )

        if (commentsState.isLoading) {
            TopCircleProgressView()
        }
    }
}

@ExperimentalPagerApi
@Composable
fun PostDetailsView(
    postDetailsState: PostDetailsResult,
    commentsState: CommentsResult,
    paddingValues: PaddingValues,
    onAvatarClicked: (IUser) -> Unit,
    onPostLikeClicked: (PostItemUI) -> Unit,
    onCommentLikeClicked: (CommentItemUI) -> Unit = {}
) {

    postDetailsState.applyIfSuccess { post ->
        val postPhotos: List<String> = post.postContent
        val topPaddings = paddingValues.calculateTopPadding()
        val bottomPaddings = topPaddings - 8.dp

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(top = topPaddings, bottom = bottomPaddings)) {

            item {
                PostContentView(postPhotos)
            }

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
    }
}