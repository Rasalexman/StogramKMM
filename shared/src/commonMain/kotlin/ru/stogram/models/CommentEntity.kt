package ru.stogram.models

import ru.stogram.utils.getRandomDate
import ru.stogram.utils.getRandomString
import ru.stogram.utils.randomBool
import ru.stogram.utils.randomCount
import kotlin.random.Random

class CommentEntity {
    var id: String = ""
    var postId: String = ""
    var user: IUser = UserEntity.createRandom(false)
    var isLiked: Boolean = false
    var text: String = ""
    var likesCount: String = "56"
    var date: String = "03.05.2022 21:55"

    companion object {
        fun createRandomList(): List<CommentEntity> {
            val createData = mutableListOf<CommentEntity>()
            val randomInt: Int = Random.nextInt(24, 56)
            repeat(randomInt) {
                createData.add(createRandom())
            }
            return createData
        }

        fun createRandom(): CommentEntity {
            return CommentEntity().apply {
                id = getRandomString(1000)
                postId = getRandomString(1000)
                isLiked = randomBool
                text = getRandomString(300)
                likesCount = randomCount
                date = getRandomDate()
            }
        }
    }
}