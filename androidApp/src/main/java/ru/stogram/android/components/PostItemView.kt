package ru.stogram.android.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.ExperimentalFoundationApi
import ru.stogram.android.common.orZero
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.mappers.PostItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.PostEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostItemView(
    post: PostItemUI,
    onAvatarClicked: (PostItemUI) -> Unit,
    onCommentsClicked: (PostItemUI) -> Unit,
    onLikeClicked: (PostItemUI) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)) {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            AvatarNameDescView(user = post.user) {
                onAvatarClicked(post)
            }
        }

        PostContentView(post.postContent)

        Row(modifier = Modifier.padding(all = 8.dp)) {

            LikesView(count = post.likesCount.orZero(), isSelected = post.isLiked) {
                onLikeClicked(post)
            }

            Spacer(modifier = Modifier.padding(start = 16.dp))

            CommentsView(count = post.commentsCount.orZero()) {
                onCommentsClicked(post)
            }
        }

        Text(
            text = post.text,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            fontSize = 14.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

class PostPreviewParameterProvider : PreviewParameterProvider<PostItemUI> {
    private val postItemUIMapper: IPostItemUIMapper = PostItemUIMapper()
    override val values = sequenceOf(
        postItemUIMapper.convertSingle(PostEntity.createRandom()),
        postItemUIMapper.convertSingle(PostEntity.createRandom()),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Suppress("PreviewAnnotationInFunctionWithParameters")
@Preview(name = "PostView", showBackground = true)
@Composable
fun PostViewPreview(
    @PreviewParameter(PostPreviewParameterProvider::class, limit = 1) post: PostItemUI
) {
    PostItemView(
        post = post,
        onAvatarClicked = {},
        onCommentsClicked = {},
        onLikeClicked = {}
    )
}