package ru.stogram.models

interface IUser {
    var id: String?
    var name: String?
    var photo: String?
    var desc: String?
    var hasStory: Boolean
}