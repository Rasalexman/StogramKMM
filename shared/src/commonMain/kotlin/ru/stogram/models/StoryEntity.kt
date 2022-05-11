package ru.stogram.models

import ru.stogram.utils.getRandomPhoto
import ru.stogram.utils.getRandomString
import kotlin.random.Random

class StoryEntity {
    var id: String = ""
    var content: String? = null
    var user: IUser? = null

    companion object {
        fun createRandomList(): List<StoryEntity> {
            val createData = mutableListOf<StoryEntity>()
            val randomInt: Int = Random.nextInt(10, 56)
            repeat(randomInt) {
                createData.add(createRandom())
            }
            return createData
        }

        fun createRandom(): StoryEntity {
            return StoryEntity().apply {
                id = getRandomString(100)
                content = getRandomPhoto()
                user = UserEntity.createRandom()
            }
        }
    }
}


