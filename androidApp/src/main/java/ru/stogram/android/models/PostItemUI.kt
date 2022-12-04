package ru.stogram.android.models

import ru.stogram.models.IUser

data class PostItemUI(
    val id: String,
    val postId: String,
    val text: String,
    val likesCount: String,
    val commentsCount: String,
    val date: String,
    val user: IUser,
    val firstPhoto: String,
    val postContent: List<String>,
    val isLiked: Boolean
)
