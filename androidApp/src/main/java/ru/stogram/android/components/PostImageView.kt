package ru.stogram.android.components

import androidx.compose.runtime.Composable
import ru.stogram.models.PostEntity

@Composable
fun PostImageView(post: PostEntity) {
    PhotoImageView(url = post.firstPhoto)
}