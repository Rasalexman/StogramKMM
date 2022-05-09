package ru.stogram.models

import io.realm.RealmObject
import ru.stogram.utils.getRandomName
import ru.stogram.utils.getRandomString
import ru.stogram.utils.randomBool
import ru.stogram.utils.randomCount
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

    companion object {
        fun createRandom(): List<PostEntity> {
            val createData = mutableListOf<PostEntity>()
            val randomInt: Int = Random.nextInt(24, 48)
            repeat(randomInt) {
                createData.add(
                    PostEntity().apply {
                        id = getRandomString(100)
                        postId = getRandomString(100)
                        userId = getRandomString(100)
                        userName = getRandomName()
                        userPhoto = getRandomString(10)
                        location = "Saint-Petersburg"
                        text = getRandomString(200)
                        likesCount = randomCount
                        commentsCount = randomCount
                        isLiked = randomBool
                        hasStory = randomBool
                        date = "10.05.2022"
                    }
                )
            }
            return createData
        }
    }
}


