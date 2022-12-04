package ru.stogram.android.features.comments

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.stogram.android.common.orZero
import ru.stogram.android.components.AvatarNameDescView
import ru.stogram.android.components.LikesView
import ru.stogram.android.mappers.CommentItemUIMapper
import ru.stogram.android.mappers.ICommentItemUIMapper
import ru.stogram.android.models.CommentItemUI
import ru.stogram.models.CommentEntity
import ru.stogram.models.IUser

@Composable
fun CommentItemView(
    comment: CommentItemUI,
    onAvatarClicked: (IUser) -> Unit,
    onLikeClicked: (CommentItemUI) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {

        AvatarNameDescView(user = comment.user, desc = comment.date) {
            onAvatarClicked.invoke(comment.user)
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
                isSelected = comment.isLiked,
                textSize = 12.sp,
                iconSize = 24.dp
            ) {
                onLikeClicked.invoke(comment)
            }
        }
    }

}

class CommentItemPreviewParameterProvider : PreviewParameterProvider<CommentItemUI> {
    private val commentItemUIMapper: ICommentItemUIMapper = CommentItemUIMapper()
    override val values = sequenceOf(
        commentItemUIMapper.convertSingle(CommentEntity.createRandom())
    )
}

@Preview(name = "CommentItemView", showBackground = true)
@Composable
fun CommentItemViewPreview(
    @PreviewParameter(CommentItemPreviewParameterProvider::class, limit = 1) comment: CommentItemUI
) {
    CommentItemView(comment = comment, onAvatarClicked = {}) {

    }
}