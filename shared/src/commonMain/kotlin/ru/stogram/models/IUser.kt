package ru.stogram.models

interface IUser {
    var id: String?
    var name: String?
    var photo: String?
    var desc: String?
    var hasStory: Boolean

    var postCount: String?
    var subsCount: String?
    var observCount: String?
    var bio: String?
}

fun IUser?.orEmpty(): IUser {
    return UserEntity.createRandom()
}