package ru.stogram.models

import io.realm.kotlin.types.RealmObject
import kotlinx.datetime.Clock
import ru.stogram.utils.getRandomDate
import ru.stogram.utils.getRandomString
import ru.stogram.utils.randomBool
import ru.stogram.utils.randomCount
import kotlin.random.Random

class CommentEntity : RealmObject {
    var id: String = ""
    var postId: String = ""
    var user: UserEntity? = null
    var isLiked: Boolean = false
    var text: String = ""
    var likesCount: String = "0"
    var date: String = getRandomDate()
    var createdAt: Long = 0

    fun takeCommentUser(): IUser {
        return user ?: UserEntity.createRandom(randomBool)
    }

    fun updateLike(): Boolean {
        val likeState = !isLiked
        val lastLikesCount = likesCount.toIntOrNull() ?: 0
        val currentLikes = if(likeState) {
            lastLikesCount + 1
        } else {
            lastLikesCount - 1
        }

        likesCount = currentLikes.toString()
        isLiked = likeState
        return likeState
    }

    companion object {
        fun createRandomList(postId: String): List<CommentEntity> {
            val createData = mutableListOf<CommentEntity>()
            val randomInt: Int = Random.nextInt(3, 20)
            repeat(randomInt) {
                createData.add(createRandom(postId, randomInt.toLong()))
            }
            return createData
        }

        fun createRandom(forPostId: String? = null, index: Long = 10L): CommentEntity {
            return CommentEntity().apply {
                id = getRandomString(100)
                postId = forPostId ?: getRandomString(100)
                text = getRandomString(356)
                likesCount = randomCount
                user = UserEntity.createRandomDetailed(randomBool)
                createdAt = Clock.System.now().epochSeconds + index
            }
        }
    }
}