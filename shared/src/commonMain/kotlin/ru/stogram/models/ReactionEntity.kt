package ru.stogram.models

import io.realm.RealmObject
import ru.stogram.utils.getRandomReactionType
import ru.stogram.utils.getRandomReactionDateText
import ru.stogram.utils.getRandomString
import ru.stogram.utils.randomBool
import kotlin.random.Random

class ReactionEntity : RealmObject {
    var id: String = ""
    var type: String = ""
    var post: PostEntity? = null
    var from: UserEntity? = null
    var comment: String = ""
    var liked: Boolean = false
    var date: String = ""

    fun takeUserFrom(): IUser {
        return from!!
    }

    fun takeReactionPost(): PostEntity {
        return post!!
    }

    fun createFullDescription(): String {
        var desc = "${from?.name.orEmpty()} $date"
        when (type) {
            photoLike ->
                desc += " поставил(а) лайк на вашу фотографию"
            photoComment ->
                desc += " оставил(а) коммент на вашу фотографию: $comment"
            historyComment ->
                desc += " оставил(а) коммент на вашу историю: $comment"
            likeOnComment ->
                desc += " поставил(а) лайк на ваш комментарий"
        }

        return desc
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
                createData.add(createRandom().apply {
                    post = PostEntity.createRandom()
                    from = UserEntity.createRandomDetailed(randomBool)
                })
            }
            return createData
        }

        fun createRandom(): ReactionEntity {
            return ReactionEntity().apply {
                id = getRandomString(100)
                type = getRandomReactionType()
                comment = getRandomString(300)
                liked = randomBool
                date = getRandomReactionDateText()
            }
        }
    }
}