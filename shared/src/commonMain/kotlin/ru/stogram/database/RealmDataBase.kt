package ru.stogram.database

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import ru.stogram.models.CommentEntity
import ru.stogram.models.PostEntity
import ru.stogram.models.ReactionEntity
import ru.stogram.models.UserEntity

class RealmDataBase {
    private val configuration = RealmConfiguration.create(
        schema = setOf(
            UserEntity::class,
            PostEntity::class,
            ReactionEntity::class,
            CommentEntity::class
        ))

    val realm: Realm by lazy {
        println("--------> open real with config")
        Realm.open(configuration)
            //.deleteRealmIfMigrationNeeded().schemaVersion(1).build()
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