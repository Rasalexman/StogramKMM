package ru.stogram.models

import ru.stogram.utils.*

class UserEntity : IUser {
    override var id: String? = ""
    override var name: String? = null
    override var photo: String? = null
    override var desc: String? = null
    override var hasStory: Boolean = false

    companion object {
        fun createRandom(): IUser {
            return UserEntity().apply {
                id = getRandomString(100)
                name = getRandomName()
                photo = getRandomPhoto()
                desc = randomLocation
                hasStory = randomBool
            }
        }
    }
}