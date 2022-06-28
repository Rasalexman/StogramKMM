package ru.stogram.repository

import com.rasalexman.sresult.data.dto.SResult
import kotlinx.coroutines.flow.Flow
import ru.stogram.database.CFlow
import ru.stogram.database.wrap
import ru.stogram.models.UserEntity
import ru.stogram.sources.local.IUserLocalDataSource

class UserRepository(
    private val localDataSource: IUserLocalDataSource
) : IUserRepository {

    override fun findUserDetailsAsFlow(userId: String): Flow<UserEntity?> {
        return localDataSource.findUserDetailsAsFlow(userId)
    }

    override fun findUserDetailsAsCommonFlow(userId: String): CFlow<UserEntity?> {
        return findUserDetailsAsFlow(userId).wrap()
    }

    override fun takeUserResult(): SResult<UserEntity> {
        return SResult.Success(UserEntity.createRandom())
    }
}

interface IUserRepository {
    fun findUserDetailsAsFlow(userId: String): Flow<UserEntity?>
    fun findUserDetailsAsCommonFlow(userId: String): CFlow<UserEntity?>

    fun takeUserResult(): SResult<UserEntity>
}