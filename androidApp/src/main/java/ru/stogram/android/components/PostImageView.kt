package ru.stogram.android.components

import androidx.compose.runtime.Composable
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.PostEntity

@Composable
fun PostImageView(post: PostItemUI, onClick: ((PostItemUI) -> Unit)? = null) {
    PhotoImageView(url = post.firstPhoto) {
        onClick?.invoke(post)
    }
}