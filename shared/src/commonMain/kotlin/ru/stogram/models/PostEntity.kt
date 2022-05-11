package ru.stogram.models

import io.realm.RealmObject
import ru.stogram.utils.*
import kotlin.random.Random

class PostEntity : RealmObject {
    var id: String = ""
    var postId: String? = null
    var userId: String? = null
    var userName: String? = null
    var userPhoto: String? = null
    var location: String? = null
    var text: String? = null
    var likesCount: String? = null
    var commentsCount: String? = null
    var isLiked: Boolean = false
    var hasStory: Boolean = false
    var date: String? = null

    //
    var content: List<String> = getRandomPhotoList()

    val firstPhoto: String
        get() = content.firstOrNull() ?: getRandomPhoto()

    fun createUser(): IUser {
        val hasUserStory = hasStory
        return UserEntity().apply {
            this.id = userId
            this.name = userName
            this.photo = userPhoto
            this.desc = location
            this.hasStory = hasUserStory
        }
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
                userId = getRandomString(100)
                userName = getRandomName()
                userPhoto = getRandomPhoto()
                location = randomLocation
                text = getRandomString(300)
                likesCount = randomCount
                commentsCount = randomCount
                isLiked = randomBool
                hasStory = randomBool
                content = getRandomPhotoList()
                date = "10.05.2022"
            }
        }
    }
}


