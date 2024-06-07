package ru.stogram.models.domain

data class PostModel(
    val id: String = "",
    val postId: String = "",
    val text: String = "",
    val likesCount: String = "0",
    val commentsCount: String = "0",
    val isLiked: Boolean = false,
    val date: String = "",
    val user: UserModel,
    val content: List<String> = emptyList()
)
