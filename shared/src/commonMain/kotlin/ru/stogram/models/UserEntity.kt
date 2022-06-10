package ru.stogram.models

import io.realm.kotlin.types.RealmObject
import ru.stogram.utils.*
import kotlin.random.Random

class UserEntity : RealmObject, IUser {
    override var id: String = ""
    override var name: String = ""
    override var photo: String = ""
    override var desc: String = ""
    override var hasStory: Boolean = false

    override var postCount: String = "0"
    override var subsCount: String = "0"
    override var observCount: String = "0"
    override var bio: String = ""

    companion object {

        const val DEFAULT_USER_ID = "sdsa23das34fdfg434sdGsa"

        fun createRandomList(hasUserStory: Boolean? = null): List<UserEntity> {
            val createData = mutableListOf<UserEntity>()
            val randomInt: Int = Random.nextInt(10, 56)
            repeat(randomInt) {
                createData.add(createRandom(hasUserStory))
            }
            return createData
        }

        fun createRandom(hasUserStory: Boolean? = null): UserEntity {
            return UserEntity().apply {
                id = getRandomString(100)
                name = getRandomName()
                photo = getRandomPhoto()
                desc = randomLocation
                hasStory = hasUserStory ?: randomBool
            }
        }

        fun createRandomDetailed(hasUserStory: Boolean? = null): UserEntity {
            return createRandom(hasUserStory).apply {
                postCount = randomCount
                subsCount = randomCount
                observCount = randomCount
                bio = getRandomString(156)
            }
        }

        fun createDefaultUser(): UserEntity {
            return createRandomDetailed(true).apply {
                id = DEFAULT_USER_ID
                name = "Aleksandr Minkin"
            }
        }
    }
}