package ru.stogram.android.models

import ru.stogram.models.IUser

data class CommentItemUI(
    val id: String,
    val postId: String,
    val user: IUser,
    val isLiked: Boolean,
    val text: String,
    val likesCount: String,
    val date: String
)
