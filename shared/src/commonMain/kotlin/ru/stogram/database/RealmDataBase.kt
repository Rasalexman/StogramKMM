package ru.stogram.database

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.query
import ru.stogram.models.CommentEntity
import ru.stogram.models.PostEntity
import ru.stogram.models.ReactionEntity
import ru.stogram.models.UserEntity

class RealmDataBase {
    val realm: Realm by lazy {
        val configuration = RealmConfiguration.with(
            schema = setOf(
                PostEntity::class,
                UserEntity::class,
                ReactionEntity::class,
                CommentEntity::class
            ))
            //.deleteRealmIfMigrationNeeded().schemaVersion(1).build()
        Realm.open(configuration)
    }

    private var localUser: UserEntity? = null

    fun getCurrentUser(): UserEntity {
        return localUser ?: fetchRealUser()
    }

    private fun fetchRealUser(): UserEntity {
        val dbUser: UserEntity? = realm.query<UserEntity>("id = $0", UserEntity.DEFAULT_USER_ID).first().find()
        val realUser = dbUser ?: createDefaultUser()
        localUser = realUser
        return realUser
    }

    private fun createDefaultUser(): UserEntity {
        return realm.writeBlocking {
            copyToRealm(UserEntity.createDefaultUser())
        }
    }
}