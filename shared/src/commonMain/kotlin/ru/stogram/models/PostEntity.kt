package ru.stogram.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.models.domain.PostModel
import ru.stogram.models.domain.UserModel
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

    fun takeContent(): List<String> {
        return content.toList()
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
        fun createRandomList(): List<PostEntity> {
            val createData = mutableListOf<PostEntity>()
            val randomInt: Int = Random.nextInt(24, 56)
            repeat(randomInt) {
                createData.add(createRandom())
            }
            return createData
        }

        fun createRandom(defaultUser: UserEntity? = null): PostEntity {
            return PostEntity().apply {
                id = getRandomString(100)
                postId = getRandomString(100)
                text = getRandomString(300)
                likesCount = randomCount
                commentsCount = randomCount
                isLiked = randomBool
                user = defaultUser ?: UserEntity.createRandomDetailed(randomBool)
                date = getRandomDate()
                content.addAll(getRandomPhotoList())
            }
        }

        fun createRandomWithoutUser(): PostEntity {
            return PostEntity().apply {
                id = getRandomString(100)
                postId = getRandomString(100)
                text = getRandomString(300)
                likesCount = randomCount
                commentsCount = randomCount
                isLiked = randomBool
                date = getRandomDate()
                content.addAll(getRandomPhotoList())
            }
        }
    }
}

fun PostEntity?.orEmpty(): PostEntity {
    return this ?: PostEntity.createRandom()
}

fun PostEntity.toDomain(user: UserModel): PostModel {
    return PostModel(
        id, postId, text, likesCount, commentsCount, isLiked, date, user, takeContent()
    )
}


