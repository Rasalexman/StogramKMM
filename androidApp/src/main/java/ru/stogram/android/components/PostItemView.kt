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
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.stogram.android.common.orZero
import ru.stogram.android.features.home.HomeViewModel
import ru.stogram.models.PostEntity

@ExperimentalPagerApi
@Composable
fun PostItemView(
    post: PostEntity,
    viewModel: HomeViewModel
) {

    var isLikedState by remember { mutableStateOf(post.isLiked) }
    val postPhotos: List<String> by post.takeContentFlow().collectAsState(
        initial = emptyList()
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)) {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            AvatarNameDescView(user = post.takePostUser()) {
                viewModel.onPostAvatarClicked(post = post)
            }
        }

        PostContentView(postPhotos)

        Row(modifier = Modifier.padding(all = 8.dp)) {

            LikesView(count = post.likesCount.orZero(), isSelected = isLikedState) {
                //post.isLiked = !post.isLiked
                isLikedState = post.isLiked
            }

            Spacer(modifier = Modifier.padding(start = 16.dp))

            CommentsView(count = post.commentsCount.orZero()) {
                viewModel.onPostCommentsClicked(post = post)
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

class PostPreviewParameterProvider : PreviewParameterProvider<PostEntity> {
    override val values = sequenceOf(
        PostEntity.createRandom(),
        PostEntity.createRandom(),
    )
}

@ExperimentalPagerApi
@Suppress("PreviewAnnotationInFunctionWithParameters")
@Preview(name = "PostView", showBackground = true)
@Composable
fun PostViewPreview(
    @PreviewParameter(PostPreviewParameterProvider::class, limit = 1) post: PostEntity
) {
    PostItemView(post = post, viewModel = HomeViewModel())
}