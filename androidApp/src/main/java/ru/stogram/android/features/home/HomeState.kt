package ru.stogram.android.features.home

import ru.stogram.android.models.PostItemUI
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

data class HomeState(
    val posts: List<PostItemUI>,
    val stories: List<UserEntity>
)
