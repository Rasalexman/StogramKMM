package ru.stogram.android.models

import ru.stogram.models.IUser

data class ReactionItemUI(
    val id: String,
    val type: String,
    val post: PostItemUI,
    val user: IUser,
    val comment: String,
    val liked: Boolean,
    val date: String,
    val description: String
)
