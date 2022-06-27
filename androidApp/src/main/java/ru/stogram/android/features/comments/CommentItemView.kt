package ru.stogram.android.features.comments

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.stogram.android.common.orZero
import ru.stogram.android.components.AvatarNameDescView
import ru.stogram.android.components.LikesView
import ru.stogram.models.CommentEntity

@Composable
fun CommentItemView(comment: CommentEntity, onAvatarClicked: (CommentEntity) -> Unit) {

    var isLikedState by remember { mutableStateOf(comment.isLiked) }

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {

        AvatarNameDescView(user = comment.takeCommentUser(), desc = comment.date) {
            onAvatarClicked.invoke(comment)
        }

        Text(
            text = comment.text,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            LikesView(
                count = comment.likesCount.orZero(),
                isSelected = isLikedState,
                textSize = 12.sp,
                iconSize = 24.dp
            ) {
                isLikedState = !isLikedState
            }
        }
    }

}

class CommentItemPreviewParameterProvider : PreviewParameterProvider<CommentEntity> {
    override val values = sequenceOf(
        CommentEntity.createRandom()
    )
}

@Preview(name = "CommentItemView", showBackground = true)
@Composable
fun CommentItemViewPreview(
    @PreviewParameter(CommentItemPreviewParameterProvider::class, limit = 1) comment: CommentEntity
) {
    CommentItemView(comment = comment) {

    }
}