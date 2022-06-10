package ru.stogram.sources.local

import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.stogram.database.RealmDataBase
import ru.stogram.models.UserEntity

class UserLocalDataSource(
    private val database: RealmDataBase
) : IUserLocalDataSource {

    override fun findUserDetailsAsFlow(userId: String): Flow<UserEntity?> {
        return if(userId == UserEntity.DEFAULT_USER_ID) {
            flow { emit(database.getCurrentUser()) }
        } else {
            val searchUserQuery = database.realm.query<UserEntity>("id = $0", userId)
            searchUserQuery.asFlow().map { result ->
                val foundUsers = result.list.toList()
                println("found users count = ${foundUsers.size}")
                val foundUser = foundUsers.firstOrNull()
                println("current userName = ${foundUser?.name ?: "NOT FOUND"}")
                foundUser
            }
        }
    }
}

interface IUserLocalDataSource {
    fun findUserDetailsAsFlow(userId: String): Flow<UserEntity?>
}