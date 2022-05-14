package ru.stogram.models

import io.realm.RealmObject
import ru.stogram.utils.*
import kotlin.random.Random

class PostEntity : RealmObject {
    var id: String = ""
    var postId: String? = null
    var text: String? = null
    var likesCount: String? = null
    var commentsCount: String? = null
    var isLiked: Boolean = false
    var date: String? = null

    var user: IUser? = null

    //
    var content: List<String> = getRandomPhotoList()

    val firstPhoto: String
        get() = content.firstOrNull() ?: getRandomPhoto()

    fun createUser(): IUser {
        return user ?: UserEntity.createRandom()
    }

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
                date = "10.05.2022"
                user = UserEntity.createRandom()
            }
        }
    }
}

fun PostEntity?.orEmpty(): PostEntity {
    return this ?: PostEntity.createRandom()
}


