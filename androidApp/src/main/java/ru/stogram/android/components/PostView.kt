package ru.stogram.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import ru.stogram.android.R
import ru.stogram.android.common.orZero
import ru.stogram.models.PostEntity

@Composable
fun PostView(post: PostEntity) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp)) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            AvatarNameDescView(user = post.createUser())
        }

        if (LocalInspectionMode.current) {
            val painter = painterResource(id = R.drawable.default_avatar)
            Image(
                painter = painter,
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        } else {
            val painter =
                rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(post.createRandomPhoto())
                        .size(Size.ORIGINAL) // Set the target size to load the image at.
                        .build()
                )
            Image(
                painter = painter,
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }

        Row(modifier = Modifier.padding(all = 8.dp)) {
            LikesView(count = post.likesCount.orZero(), isSelected = post.isLiked) {

            }

            Spacer(modifier = Modifier.padding(start = 16.dp))

            CommentsView(count = post.commentsCount.orZero()) {

            }
        }

        Text(
            text = post.text.orEmpty(),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

class PostPreviewParameterProvider : PreviewParameterProvider<PostEntity> {
    override val values = sequenceOf(
        PostEntity.createRandomPost(),
        PostEntity.createRandomPost(),
    )
}

@Suppress("PreviewAnnotationInFunctionWithParameters")
@Preview(name = "PostView", showBackground = true)
@Composable
fun PostViewPreview(
    @PreviewParameter(PostPreviewParameterProvider::class, limit = 1) post: PostEntity
) {
    PostView(post = post)
}