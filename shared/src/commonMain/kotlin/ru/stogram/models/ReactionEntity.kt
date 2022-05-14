package ru.stogram.models

import ru.stogram.utils.getRandomReaction
import ru.stogram.utils.getRandomReactionDateText
import ru.stogram.utils.getRandomString
import ru.stogram.utils.randomBool
import kotlin.random.Random

class ReactionEntity {
    var id: String = ""
    var type: String? = null
    var post: PostEntity? = null
    var from: IUser? = null
    var comment: String? = null
    var liked: Boolean = false
    var date: String = ""

    val fullDescription: String get() {//AttributedString {
        var desc = "${from?.name.orEmpty()} $date"
        when (type) {
            ReactionEntity.photoLike ->
            desc += " поставил(а) лайк на вашу фотографию"
            ReactionEntity.photoComment ->
            desc += " оставил(а) коммент на вашу фотографию: $comment"
            ReactionEntity.historyComment ->
            desc += " оставил(а) коммент на вашу историю: $comment"
            ReactionEntity.likeOnComment ->
            desc += " поставил(а) лайк на ваш комментарий"
        }

        return desc//.markdownToAttributed()
    }

    companion object {

        const val photoLike: String = "photoLike"
        const val photoComment: String = "photoComment"
        const val historyComment: String = "historyComment"
        const val likeOnComment: String = "likeOnComment"

        fun createRandomList(): List<ReactionEntity> {
            val createData = mutableListOf<ReactionEntity>()
            val randomInt: Int = Random.nextInt(24, 56)
            repeat(randomInt) {
                createData.add(ReactionEntity().apply {
                    id = getRandomString(1000)
                    type = getRandomReaction()
                    post = PostEntity.createRandom()
                    from = UserEntity.createRandom()
                    comment = getRandomString(300)
                    liked = randomBool
                    date = getRandomReactionDateText()
                })
            }
            return createData
        }
    }
}