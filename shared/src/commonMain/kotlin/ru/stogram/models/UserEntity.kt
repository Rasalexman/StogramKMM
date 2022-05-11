package ru.stogram.models

import ru.stogram.utils.*
import kotlin.random.Random

class UserEntity : IUser {
    override var id: String? = ""
    override var name: String? = null
    override var photo: String? = null
    override var desc: String? = null
    override var hasStory: Boolean = false

    companion object {
        fun createRandomList(hasUserStory: Boolean? = null): List<IUser> {
            val createData = mutableListOf<IUser>()
            val randomInt: Int = Random.nextInt(10, 56)
            repeat(randomInt) {
                createData.add(createRandom(hasUserStory))
            }
            return createData
        }

        fun createRandom(hasUserStory: Boolean? = null): IUser {
            return UserEntity().apply {
                id = getRandomString(100)
                name = getRandomName()
                photo = getRandomPhoto()
                desc = randomLocation
                hasStory = hasUserStory ?: randomBool
            }
        }
    }
}