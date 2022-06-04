package ru.stogram.android.features.home

import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

data class HomeState(
    val posts: List<PostEntity>,
    val stories: List<UserEntity>
)
