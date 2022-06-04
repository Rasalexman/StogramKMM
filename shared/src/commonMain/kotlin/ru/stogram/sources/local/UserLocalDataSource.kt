package ru.stogram.sources.local

import io.realm.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.database.RealmDataBase
import ru.stogram.models.UserEntity

class UserLocalDataSource(
    private val database: RealmDataBase
) : IUserLocalDataSource {

    override fun findUserDetailsAsFlow(userId: String): Flow<UserEntity?> {
        return database.realm.query<UserEntity>("id = $0", userId).find().asFlow().map {
            val foundUser = it.list.firstOrNull()
            // if we pass default user id and cannot find "MY" profile
            if(userId == UserEntity.DEFAULT_USER_ID && foundUser == null) {
                database.getCurrentUser()
            } else {
                foundUser
            }
        }
    }
}

interface IUserLocalDataSource {
    fun findUserDetailsAsFlow(userId: String): Flow<UserEntity?>
}