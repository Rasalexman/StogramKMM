package ru.stogram.sources.local

import com.rasalexman.sresult.data.dto.SResult
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.stogram.database.RealmDataBase
import ru.stogram.models.IUser
import ru.stogram.models.UserEntity

class UserLocalDataSource(
    private val database: RealmDataBase
) : IUserLocalDataSource {

    override fun getCurrentUserFlow(): Flow<UserEntity> {
        return database.getCurrentUserFlow()
    }

    override fun findUserDetailsAsFlow(userId: String): Flow<UserEntity?> {
        val currentUser = database.getCurrentUser()
        val currentUserId = currentUser.id
        return if (userId.isEmpty() || (userId == currentUserId)) {
            getCurrentUserFlow()
        } else {
            val searchUserQuery = database.realm.query<UserEntity>("id = $0", userId)
            searchUserQuery.asFlow().map { result ->
                val foundUsers = result.list.toList()
                println("found users count = ${foundUsers.size}")
                val foundUser = foundUsers.firstOrNull()
                println("current userName = ${foundUser?.name ?: "NOT FOUND"}")
                foundUser?.setupIsSubs(currentUserId)
            }
        }
    }

    override fun subscribeToUser(userId: String): SResult<Boolean> {
        val isAddedToSubscribers = database.realm.writeBlocking {
            database.realm.query<UserEntity>("id = $0", userId).first().find()
                ?.let { foundedUser ->
                    val currentUser = database.getCurrentUser()
                    val isAdded = findLatest(foundedUser)?.addToSubs(currentUser.id) ?: false
                    if(isAdded) {
                        findLatest(currentUser)?.apply {
                            addToObserv(foundedUser.id)
                            copyToRealm(this)
                        }
                    }
                    isAdded
                } ?: false
        }
        return SResult.Success(isAddedToSubscribers)
    }

    override fun unsubscribeToUser(userId: String): SResult<Boolean> {
        val isAddedToSubscribers = database.realm.writeBlocking {
            database.realm.query<UserEntity>("id = $0", userId).first().find()
                ?.let { foundedUser ->
                    val currentUser = database.getCurrentUser()
                    val isRemoved = findLatest(foundedUser)?.removeFromSubs(currentUser.id) ?: false
                    if(isRemoved) {
                        findLatest(currentUser)?.apply {
                            removeFromObserv(foundedUser.id)
                            copyToRealm(this)
                        }
                    }
                    isRemoved
                } ?: false
        }
        return SResult.Success(isAddedToSubscribers)
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
        val foundedUser: UserEntity? =
            database.realm.query<UserEntity>("login = $0 AND password = $1", login, password)
                .first().find()
        return if (foundedUser == null) {
            SResult.AbstractFailure.Error("User not Registered")
        } else {
            database.createNewSession(foundedUser)
            SResult.Success(true)
        }
    }

    override fun getUserObservers(userId: String): Flow<SResult<List<IUser>>> {
        return findUserDetailsAsFlow(userId).flatMapLatest { foundedUser ->
            if(foundedUser != null) {
                val userSubs = foundedUser.observers.toList()
                getUsersResultFlow(userSubs)
            } else {
                flowOf(SResult.AbstractFailure.Error("user not found"))
            }
        }
    }

    override fun getUserSubscribers(userId: String): Flow<SResult<List<IUser>>> {
        return findUserDetailsAsFlow(userId).flatMapLatest { foundedUser ->
            if(foundedUser != null) {
                val userSubs = foundedUser.subscribers.toList()
                getUsersResultFlow(userSubs)
            } else {
                flowOf(SResult.AbstractFailure.Error("user not found"))
            }
        }
    }

    private fun getUsersResultFlow(searchedList: List<String>): Flow<SResult<List<IUser>>> {
        return if(searchedList.isNotEmpty()) {
            val types = searchedList.toTypedArray()
            val qsr = List(searchedList.size) { index -> "$$index" }.joinToString(",")
            database.realm.query<UserEntity>("id IN {$qsr}", *types).asFlow().map {
                SResult.Success(it.list.toList())
            }
        } else {
            flowOf(SResult.Empty)
        }
    }
}

interface IUserLocalDataSource {
    fun findUserDetailsAsFlow(userId: String): Flow<UserEntity?>
    fun registerUser(user: UserEntity): SResult<Boolean>
    fun authUser(login: String, password: String): SResult<Boolean>
    fun getCurrentUserFlow(): Flow<UserEntity>

    fun subscribeToUser(userId: String): SResult<Boolean>

    fun unsubscribeToUser(userId: String): SResult<Boolean>

    fun getUserSubscribers(userId: String): Flow<SResult<List<IUser>>>
    fun getUserObservers(userId: String): Flow<SResult<List<IUser>>>
}