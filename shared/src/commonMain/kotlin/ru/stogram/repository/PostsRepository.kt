package ru.stogram.repository

import com.rasalexman.sresult.data.dto.SResult
import kotlinx.coroutines.flow.Flow
import ru.stogram.database.CFlow
import ru.stogram.database.wrap
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity
import ru.stogram.models.domain.PostModel
import ru.stogram.sources.local.IPostsLocalDataSource

class PostsRepository(
    private val localDataSource: IPostsLocalDataSource
) : IPostsRepository {

    override fun findPostByIdAsFlow(postId: String): Flow<PostEntity?> {
        return localDataSource.findPostByIdAsFlow(postId)
    }

    override fun allPostsAsFlowable(): Flow<List<PostEntity>> {
        return localDataSource.getAllPostsAsFlow()
    }

    override fun allDomainPostsAsFlowable(): Flow<List<PostModel>> {
        return localDataSource.getDomainPostsAsFlow()
    }

    override fun allPostsAsCommonFlowable(): CFlow<List<PostEntity>> {
        return allPostsAsFlowable().wrap()
    }

    override fun findUserPostsAsFlow(user: UserEntity?): Flow<List<PostEntity>> {
        return localDataSource.findUserPostsFlow(user)
    }

    override fun findUserPostsAsCommonFlow(user: UserEntity?): CFlow<List<PostEntity>> {
        return findUserPostsAsFlow(user).wrap()
    }

    override fun addUserPostAsFlow(): Flow<PostEntity> {
        return localDataSource.addUserPostAsFlow()
    }

    override fun addUserPostAsCommonFlow(): CFlow<PostEntity> {
        return addUserPostAsFlow().wrap()
    }

    override fun updatePostLike(postId: String): SResult<Boolean> {
        return localDataSource.updatePostLike(postId)
    }
}

interface IPostsRepository {
    fun findPostByIdAsFlow(postId: String): Flow<PostEntity?>

    fun allPostsAsFlowable(): Flow<List<PostEntity>>

    fun allDomainPostsAsFlowable(): Flow<List<PostModel>>

    fun allPostsAsCommonFlowable(): CFlow<List<PostEntity>>

    fun findUserPostsAsFlow(user: UserEntity? = null): Flow<List<PostEntity>>
    fun findUserPostsAsCommonFlow(user: UserEntity? = null): CFlow<List<PostEntity>>

    fun addUserPostAsFlow(): Flow<PostEntity>
    fun addUserPostAsCommonFlow(): CFlow<PostEntity>

    fun updatePostLike(postId: String): SResult<Boolean>
}