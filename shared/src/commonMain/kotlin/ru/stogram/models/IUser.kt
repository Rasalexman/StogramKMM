package ru.stogram.models

interface IUser {
    var id: String
    var name: String
    var login: String
    var photo: String
    var desc: String
    var password: String
    var hasStory: Boolean

    var postCount: String
    var subsCount: String
    var observCount: String
    var bio: String

    var isCurrentUser: Boolean
}

fun IUser?.orEmpty(): IUser {
    return UserEntity.createRandom()
}