package ru.stogram.android.features.reactions

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.stogram.android.components.PostImageView
import ru.stogram.android.components.UserAvatarView
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.mappers.IReactionItemUIMapper
import ru.stogram.android.mappers.PostItemUIMapper
import ru.stogram.android.mappers.ReactionItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.android.models.ReactionItemUI
import ru.stogram.models.IUser
import ru.stogram.models.ReactionEntity


@Composable
fun ReactionItemView(
    reaction: ReactionItemUI,
    onAvatarClicked: (IUser) -> Unit,
    onPostClicked: (PostItemUI) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        UserAvatarView(user = reaction.user, onClick = onAvatarClicked)

        Text(
            text = reaction.description,
            fontSize = 12.sp,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp).weight(1.0f)
        )

        Box(modifier = Modifier.size(72.dp)) {
            PostImageView(post = reaction.post, onClick = onPostClicked)
        }
    }
}

class ReactionItemPreviewParameterProvider : PreviewParameterProvider<ReactionItemUI> {
    private val postItemUIMapper: IPostItemUIMapper = PostItemUIMapper()
    private val reactionItemUIMapper: IReactionItemUIMapper = ReactionItemUIMapper(postItemUIMapper)
    override val values = sequenceOf(
        reactionItemUIMapper.convertSingle(ReactionEntity.createRandom())
    )
}

@Preview(name = "ReactionItemView", showBackground = true)
@Composable
fun ReactionItemViewPreview(
    @PreviewParameter(ReactionItemPreviewParameterProvider::class, limit = 1) post: ReactionItemUI
) {
    ReactionItemView(reaction = post, onAvatarClicked = {}) {

    }
}