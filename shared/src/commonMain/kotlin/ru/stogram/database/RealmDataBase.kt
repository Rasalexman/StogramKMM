package ru.stogram.database

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.stogram.models.*

class RealmDataBase {
    private val configuration = RealmConfiguration.create(
        schema = setOf(
            UserEntity::class,
            PostEntity::class,
            ReactionEntity::class,
            CommentEntity::class,
            LastUserEntity::class
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

    fun getCurrentUserFlow(): Flow<UserEntity> {
        return flow {
            emit(getCurrentUser())
        }
    }

    private fun fetchRealUser(): UserEntity {
        val lastSessionUser: LastUserEntity? = realm.query<LastUserEntity>().first().find()
        val lastSessionIdOrDefault = lastSessionUser?.userId ?: UserEntity.DEFAULT_USER_ID
        val dbUser: UserEntity? = realm.query<UserEntity>("id = $0", lastSessionIdOrDefault).first().find()
        val realUser = dbUser ?: createDefaultUser()
        localUser = realUser.apply {
            isCurrentUser = true
        }
        return realUser
    }

    fun createNewSession(user: UserEntity) {
        clearLastSession()
        val lastUser = LastUserEntity().apply {
            userId = user.id
            sessionLogin = user.login
        }
        realm.writeBlocking {
            copyToRealm(lastUser)
        }
    }

    private fun clearLastSession() {
        localUser = null
        realm.writeBlocking {
            val lastSessions = this.query<LastUserEntity>().find()
            delete(lastSessions)
        }
    }

    private fun createDefaultUser(): UserEntity {
        clearLastSession()
        val defaultUser = UserEntity.createDefaultUser()
        createNewSession(defaultUser)
        return realm.writeBlocking {
            copyToRealm(defaultUser)
        }
    }
}