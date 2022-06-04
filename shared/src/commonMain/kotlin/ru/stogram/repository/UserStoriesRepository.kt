package ru.stogram.repository

import kotlinx.coroutines.flow.Flow
import ru.stogram.database.CFlow
import ru.stogram.database.wrap
import ru.stogram.models.UserEntity
import ru.stogram.sources.local.IUserStoriesLocalDataSource

class UserStoriesRepository(
    private val localDataSource: IUserStoriesLocalDataSource
) : IUserStoriesRepository {

    override fun getAllStoriesAsFlow(): Flow<List<UserEntity>> {
        return localDataSource.getAllStoriesAsFlow()
    }

    override fun getStoriesAsCommonFlow(): CFlow<List<UserEntity>> {
        return getAllStoriesAsFlow().wrap()
    }

}

interface IUserStoriesRepository {
    fun getAllStoriesAsFlow(): Flow<List<UserEntity>>
    fun getStoriesAsCommonFlow(): CFlow<List<UserEntity>>
}