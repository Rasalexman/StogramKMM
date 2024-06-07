package ru.stogram.models.domain

data class UserModel(
    val id: String = "",
    val name: String = "",
    val login: String = "",
    val photo: String = "",
    val desc: String = "",
    val password: String = "",
    val hasStory: Boolean = false,
    val bio: String = "",
    val subsCount: String,
    val observCount: String,
    val isCurrentUser: Boolean = false,
    val isSubscribed: Boolean = false
)
