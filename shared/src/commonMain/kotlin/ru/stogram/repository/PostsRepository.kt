package ru.stogram.repository

import kotlinx.coroutines.flow.Flow
import ru.stogram.database.CFlow
import ru.stogram.database.wrap
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity
import ru.stogram.sources.local.IPostsLocalDataSource

class PostsRepository(
    private val localDataSource: IPostsLocalDataSource
) : IPostsRepository {

    override fun allPostsAsFlowable(): Flow<List<PostEntity>> {
        return localDataSource.getAllPostsAsFlow()
    }

    override fun allPostsAsCommonFlowable(): CFlow<List<PostEntity>> {
        return allPostsAsFlowable().wrap()
    }

    override fun findUserPostsAsFlow(user: UserEntity): Flow<List<PostEntity>> {
        return localDataSource.findUserPostsFlow(user)
    }

    override fun findUserPostsAsCommonFlow(user: UserEntity): CFlow<List<PostEntity>> {
        return findUserPostsAsFlow(user).wrap()
    }
}

interface IPostsRepository {
    fun allPostsAsFlowable(): Flow<List<PostEntity>>
    fun allPostsAsCommonFlowable(): CFlow<List<PostEntity>>

    fun findUserPostsAsFlow(user: UserEntity): Flow<List<PostEntity>>
    fun findUserPostsAsCommonFlow(user: UserEntity): CFlow<List<PostEntity>>
}