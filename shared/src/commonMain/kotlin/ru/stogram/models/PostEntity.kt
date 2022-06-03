package ru.stogram.models

import io.realm.RealmObject
import ru.stogram.utils.*
import kotlin.random.Random

class PostEntity : RealmObject {
    var id: String = ""
    var postId: String = ""
    var text: String = ""
    var likesCount: String = "0"
    var commentsCount: String = "0"
    var isLiked: Boolean = false
    var date: String = ""
    var user: IUser = UserEntity.createRandom()

    //
    var content: List<String> = getRandomPhotoList()

    val hasMoreContent: Boolean
        get() = content.size > 1

    val firstPhoto: String
        get() = content.firstOrNull() ?: getRandomPhoto()

    companion object {
        fun createRandomList(): List<PostEntity> {
            val createData = mutableListOf<PostEntity>()
            val randomInt: Int = Random.nextInt(24, 56)
            repeat(randomInt) {
                createData.add(createRandom())
            }
            return createData
        }

        fun createRandom(): PostEntity {
            return PostEntity().apply {
                id = getRandomString(100)
                postId = getRandomString(100)
                text = getRandomString(300)
                likesCount = randomCount
                commentsCount = randomCount
                isLiked = randomBool
                content = getRandomPhotoList()
                date = getRandomDate()
            }
        }
    }
}

fun PostEntity?.orEmpty(): PostEntity {
    return this ?: PostEntity.createRandom()
}


