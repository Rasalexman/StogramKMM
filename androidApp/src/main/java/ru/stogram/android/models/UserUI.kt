package ru.stogram.android.models

import ru.stogram.models.IUser

data class UserUI(
    override var id: String,
    override var name: String,
    override var login: String,
    override var photo: String,
    override var desc: String,
    override var password: String,
    override var hasStory: Boolean,
    override var bio: String,
    override var isCurrentUser: Boolean,
    override var isSubscribed: Boolean,
    override val subsCount: String,
    override val observCount: String
) : IUser
