package ru.stogram.repository

import kotlinx.coroutines.flow.Flow
import ru.stogram.database.CFlow
import ru.stogram.database.RealmDataBase
import ru.stogram.models.PostEntity
import ru.stogram.sources.local.PostsLocalDataSource

class PostsRepository {

    private val realmDataBase = RealmDataBase()
    private val localDataSource = PostsLocalDataSource()

    fun allPostsAsFlowable(): Flow<List<PostEntity>> {
        return realmDataBase.getAllPostsAsFlow()//localDataSource.getAllPostsAsFlow()
    }

    fun allPostsAsCommonFlowable(): CFlow<List<PostEntity>> {
        return realmDataBase.getAllPostsAsCommonFlow()//localDataSource.getAllPostsAsCommonFlow()
    }
}