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
import ru.stogram.models.ReactionEntity


@Composable
fun ReactionItemView(
    reaction: ReactionEntity,
    onAvatarClicked: (ReactionEntity) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        UserAvatarView(user = reaction.takeUserFrom()) {
            onAvatarClicked.invoke(reaction)
        }


        Text(
            text = reaction.createFullDescription(),
            fontSize = 12.sp,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp).weight(1.0f)
        )

        Box(modifier = Modifier.size(72.dp)) {
            PostImageView(post = reaction.takeReactionPost())
        }

    }
}

class ReactionItemPreviewParameterProvider : PreviewParameterProvider<ReactionEntity> {
    override val values = sequenceOf(
        ReactionEntity.createRandom()
    )
}

@Preview(name = "ReactionItemView", showBackground = true)
@Composable
fun ReactionItemViewPreview(
    @PreviewParameter(ReactionItemPreviewParameterProvider::class, limit = 1) post: ReactionEntity
) {
    ReactionItemView(reaction = post) {

    }
}