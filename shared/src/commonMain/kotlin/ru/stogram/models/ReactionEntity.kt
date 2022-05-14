package ru.stogram.models

class ReactionEntity {
    var id: String? = ""
    var type: String? = null
    var post: PostEntity? = null
    var from: IUser? = null
    var comment: String? = null
    var liked: Boolean = false
}