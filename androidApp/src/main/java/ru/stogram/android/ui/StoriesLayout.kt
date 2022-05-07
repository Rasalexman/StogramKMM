package ru.stogram.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import ru.stogram.android.R
import ru.stogram.models.Story

class StoryViewProvider : PreviewParameterProvider<Story> {
    override val values: Sequence<Story> = sequenceOf(Story())

}

@Suppress("PreviewAnnotationInFunctionWithParameters")
@Preview
@Composable
fun StoryCard(
    @PreviewParameter(StoryViewProvider::class) story: Story
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
       // Image(/*...*/)
        Icon(painter = painterResource(id = R.drawable.ic_account_box_24), contentDescription = "")
        Column {
            Text(story.name)
            Text(story.lastUpdate)
        }
    }
}