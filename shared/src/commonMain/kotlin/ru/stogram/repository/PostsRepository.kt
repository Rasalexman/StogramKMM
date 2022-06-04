package ru.stogram.repository

import kotlinx.coroutines.flow.Flow
import ru.stogram.database.CFlow
import ru.stogram.models.PostEntity
import ru.stogram.sources.local.IPostsLocalDataSource

class PostsRepository(
    private val localDataSource: IPostsLocalDataSource
) : IPostsRepository {

    override fun allPostsAsFlowable(): Flow<List<PostEntity>> {
        return localDataSource.getAllPostsAsFlow()
    }

    override fun allPostsAsCommonFlowable(): CFlow<List<PostEntity>> {
        return localDataSource.getAllPostsAsCommonFlow()
    }
}

interface IPostsRepository {
    fun allPostsAsFlowable(): Flow<List<PostEntity>>
    fun allPostsAsCommonFlowable(): CFlow<List<PostEntity>>
}