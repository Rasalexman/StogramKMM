package ru.stogram.models

import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmSet
import io.realm.kotlin.types.annotations.Ignore
import ru.stogram.utils.getLogin
import ru.stogram.utils.getRandomName
import ru.stogram.utils.getRandomPhoto
import ru.stogram.utils.getRandomString
import ru.stogram.utils.randomBool
import ru.stogram.utils.randomLocation
import kotlin.random.Random

class UserEntity : RealmObject, IUser {
    override var id: String = ""
    override var name: String = ""
    override var login: String = ""
    override var photo: String = ""
    override var desc: String = ""
    override var password: String = ""
    override var hasStory: Boolean = false
    override var bio: String = ""

    //
    var subscribers: RealmSet<String> = realmSetOf()
    //
    var observers: RealmSet<String> = realmSetOf()

    @Ignore
    override val subsCount: String
        get() = subscribers.size.toString()

    @Ignore
    override val observCount: String
        get() = observers.size.toString()

    @Ignore
    override var isSubscribed: Boolean = false

    @Ignore
    override var isCurrentUser: Boolean = false

    fun setupIsSubs(userId: String): UserEntity {
        isSubscribed = subscribers.contains(userId)
        return this
    }

    fun addToSubs(userId: String): Boolean {
        return if(!subscribers.contains(userId)) {
            subscribers.add(userId)
        } else {
            true
        }
    }

    fun removeFromSubs(userId: String): Boolean {
        return if(subscribers.contains(userId)) {
            subscribers.remove(userId)
        } else {
            true
        }
    }

    fun addToObserv(userId: String) {
        if(!observers.contains(userId)) {
            observers.add(userId)
        }
    }

    fun removeFromObserv(userId: String) {
        if(observers.contains(userId)) {
            observers.remove(userId)
        }
    }

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
            val userName = getRandomName()
            return UserEntity().apply {
                id = getRandomString(100)
                login = userName.getLogin()
                name = userName
                photo = getRandomPhoto()
                desc = randomLocation
                hasStory = hasUserStory ?: randomBool
                password = "123"
            }
        }

        fun createRandomDetailed(hasUserStory: Boolean? = null): UserEntity {
            return createRandom(hasUserStory).apply {
                bio = getRandomString(156)
            }
        }

        fun createDefaultUser(): UserEntity {
            val defaultName = "Aleksandr Minkin"
            return createRandomDetailed(true).apply {
                id = DEFAULT_USER_ID
                name = defaultName
                login = defaultName.getLogin()
            }
        }
    }
}