package ru.stogram.sources.local

import com.rasalexman.sresult.data.dto.SResult
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.stogram.database.RealmDataBase
import ru.stogram.models.UserEntity

class UserLocalDataSource(
    private val database: RealmDataBase
) : IUserLocalDataSource {

    override fun getCurrentUserFlow(): Flow<UserEntity> {
        return database.getCurrentUserFlow()
    }

    override fun findUserDetailsAsFlow(userId: String): Flow<UserEntity?> {
        return if (userId.isEmpty() || (userId == database.getCurrentUser().id)) {
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

    override fun registerUser(user: UserEntity): SResult<Boolean> {
        val foundedUser = database.realm.query<UserEntity>("login = $0", user.login).first().find()
        return if (foundedUser == null) {
            // новая сессия
            database.createNewSession(user)
            // запись
            database.realm.writeBlocking {
                copyToRealm(user)
            }
            SResult.Success(true)
        } else {
            SResult.AbstractFailure.Error("User Already Registered")
        }
    }

    override fun authUser(login: String, password: String): SResult<Boolean> {
        val foundedUser: UserEntity? = database.realm.query<UserEntity>("login = $0 AND password = $1", login, password).first().find()
        return if(foundedUser == null) {
            SResult.AbstractFailure.Error("User not Registered")
        } else {
            database.createNewSession(foundedUser)
            SResult.Success(true)
        }
    }
}

interface IUserLocalDataSource {
    fun findUserDetailsAsFlow(userId: String): Flow<UserEntity?>
    fun registerUser(user: UserEntity): SResult<Boolean>
    fun authUser(login: String, password: String): SResult<Boolean>
    fun getCurrentUserFlow(): Flow<UserEntity>
}