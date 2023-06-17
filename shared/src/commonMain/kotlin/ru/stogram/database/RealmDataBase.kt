package ru.stogram.database

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.models.CommentEntity
import ru.stogram.models.LastUserEntity
import ru.stogram.models.PostEntity
import ru.stogram.models.ReactionEntity
import ru.stogram.models.UserEntity

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

    fun updateUser() {
        localUser = null
        fetchRealUser()
    }

    fun getCurrentUserFlow(): Flow<UserEntity> {
        return realm.query<UserEntity>("id = $0", getCurrentUser().id).asFlow().map { result ->
            val foundUsers = result.list.toList()
            val currentUser = foundUsers.firstOrNull() ?: getCurrentUser()
            currentUser.apply { isCurrentUser = true }
        }
    }

    private fun fetchRealUser(): UserEntity {
        val lastSessionIdOrDefault = takeLastSessionOrDefaultUserId()
        val dbUser: UserEntity? = realm.query<UserEntity>("id = $0", lastSessionIdOrDefault).first().find()
        val realUser = dbUser ?: createDefaultUser()
        localUser = realUser.apply {
            isCurrentUser = true
        }
        return realUser
    }

    private fun takeLastSessionOrDefaultUserId(): String {
        val lastSessionUser: LastUserEntity? = realm.query<LastUserEntity>().first().find()
        return lastSessionUser?.userId ?: UserEntity.DEFAULT_USER_ID
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