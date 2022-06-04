package ru.stogram.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.realmListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    var user: UserEntity? = null

    //
    var content: RealmList<String> = realmListOf()

    fun takeContentFlow(): Flow<List<String>> = content.asFlow().map {
        it.list.toList()
    }

    fun takeFirstPhoto(): String {
        return content.firstOrNull() ?: getRandomPhoto()
    }

    fun takePostUser(): IUser {
        return user ?: UserEntity.createRandom(randomBool)
    }

    fun hasMoreContent(): Boolean {
        return content.size > 1
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
                user = UserEntity.createRandom(randomBool)
                date = getRandomDate()
                content.addAll(getRandomPhotoList())
            }
        }
    }
}

fun PostEntity?.orEmpty(): PostEntity {
    return this ?: PostEntity.createRandom()
}


