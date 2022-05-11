package ru.stogram.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import ru.stogram.android.R
import ru.stogram.android.common.orZero
import ru.stogram.models.PostEntity

@ExperimentalPagerApi
@Composable
fun PostView(post: PostEntity) {

    var isLikedState by remember { mutableStateOf(post.isLiked) }
    //val postContent by remember { mutableStateOf(post.content.orEmpty()) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)) {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            AvatarNameDescView(user = post.createUser())
        }

        PostContentView(post.content)

        Row(modifier = Modifier.padding(all = 8.dp)) {

            LikesView(count = post.likesCount.orZero(), isSelected = isLikedState) {
                post.isLiked = !post.isLiked
                isLikedState = post.isLiked
            }

            Spacer(modifier = Modifier.padding(start = 16.dp))

            CommentsView(count = post.commentsCount.orZero()) {

            }
        }

        Text(
            text = post.text.orEmpty(),
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
    PostView(post = post)
}