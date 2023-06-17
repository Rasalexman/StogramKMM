package ru.stogram.models

interface IUser {
    var id: String
    var name: String
    var login: String
    var photo: String
    var desc: String
    var password: String
    var hasStory: Boolean
    var bio: String

    var isCurrentUser: Boolean

    /**
     *
     */
    var isSubscribed: Boolean

    /**
     *
     */
    val subsCount: String

    /**
     *
     */
    val observCount: String
}

fun IUser?.orEmpty(): IUser {
    return UserEntity.createRandom()
}