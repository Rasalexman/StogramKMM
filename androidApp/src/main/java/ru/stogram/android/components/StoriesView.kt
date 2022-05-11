package ru.stogram.android.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import ru.stogram.android.common.orRandom
import ru.stogram.models.IUser
import ru.stogram.models.UserEntity

@Composable
fun StoriesView(stories: List<IUser>, onClick: (IUser) -> Unit) {
    LazyRow(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp)) {
        items(
            items = stories,
            key = { it.id.orRandom() }
        ) { user ->
            Spacer(modifier = Modifier.padding(start = 8.dp))
            UserAvatarView(user = user, size = 64.dp, onClick = onClick)
        }
    }
}

class StoriesPreviewParameterProvider : PreviewParameterProvider<List<IUser>> {
    override val values = sequenceOf(
        UserEntity.createRandomList(true)
    )
}

@Preview(name = "StoriesPreview", showBackground = true)
@Composable
fun StoriesPreview(
    @PreviewParameter(StoriesPreviewParameterProvider::class, limit = 1) stories: List<IUser>
) {
    StoriesView(stories = stories) { user ->
        println("-----> clicked user name: ${user.name}")
    }
}