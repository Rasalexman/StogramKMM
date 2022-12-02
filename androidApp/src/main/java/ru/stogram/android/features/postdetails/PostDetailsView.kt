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
import androidx.compose.runtime.*
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
import ru.stogram.android.R
import ru.stogram.android.common.orZero
import ru.stogram.android.common.rememberStateWithLifecycle
import ru.stogram.android.components.AvatarNameDescView
import ru.stogram.android.components.LikesView
import ru.stogram.android.components.PostContentView
import ru.stogram.android.constants.CommentsResult
import ru.stogram.android.constants.PostDetailsResult
import ru.stogram.android.features.comments.CommentItemView
import ru.stogram.android.features.comments.CommentViewModel
import ru.stogram.models.CommentEntity
import ru.stogram.models.UserEntity

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
    commentsViewModel: CommentViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val postDetailsState by rememberStateWithLifecycle(stateFlow = postDetailsViewModel.postState)
    val commentsState by rememberStateWithLifecycle(stateFlow = commentsViewModel.commentsState)

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    postDetailsState.applyIfSuccess { post ->
                        if(post.takePostUser().id != UserEntity.DEFAULT_USER_ID) {
                            AvatarNameDescView(user = post.takePostUser(), size = 32.dp)
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
        PostDetailsView(
            postDetailsState = postDetailsState,
            commentsState = commentsState,
            paddingValues = paddings,
            onAvatarClicked = postDetailsViewModel::onAvatarClicked
        )
    }
}

@ExperimentalPagerApi
@Composable
fun PostDetailsView(
    postDetailsState: PostDetailsResult,
    commentsState: CommentsResult,
    paddingValues: PaddingValues,
    onAvatarClicked: (CommentEntity) -> Unit
) {

    postDetailsState.applyIfSuccess { post ->
        var isLikedState by remember { mutableStateOf(post.isLiked) }
        val postPhotos: List<String> by post.takeContentFlow().collectAsState(
            initial = emptyList()
        )
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
                    LikesView(count = post.likesCount.orZero(), isSelected = isLikedState) {
                        //post.isLiked = !post.isLiked
                        isLikedState = !isLikedState
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
                        onAvatarClicked = onAvatarClicked
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