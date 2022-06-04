package ru.stogram.models

import io.realm.RealmObject
import ru.stogram.utils.getRandomDate
import ru.stogram.utils.getRandomString
import ru.stogram.utils.randomBool
import ru.stogram.utils.randomCount
import kotlin.random.Random

class CommentEntity : RealmObject {
    var id: String = ""
    var postId: String = ""
    var user: UserEntity? = null
    var isLiked: Boolean = randomBool
    var text: String = ""
    var likesCount: String = "56"
    var date: String = getRandomDate()

    fun takeCommentUser(): IUser {
        return user ?: UserEntity.createRandom(randomBool)
    }

    companion object {
        fun createRandomList(postId: String): List<CommentEntity> {
            val createData = mutableListOf<CommentEntity>()
            val randomInt: Int = Random.nextInt(24, 56)
            repeat(randomInt) {
                createData.add(createRandom(postId))
            }
            return createData
        }

        fun createRandom(forPostId: String? = null): CommentEntity {
            return CommentEntity().apply {
                id = getRandomString(100)
                postId = forPostId ?: getRandomString(100)
                text = getRandomString(128)
                likesCount = randomCount
                user = UserEntity.createRandomDetailed(randomBool)
            }
        }
    }
}