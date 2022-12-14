package ru.stogram.sources.local

import com.rasalexman.sresult.data.dto.SResult
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.stogram.database.RealmDataBase
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

class PostsLocalDataSource(
    private val database: RealmDataBase
) : IPostsLocalDataSource {

    override fun findPostByIdAsFlow(postId: String): Flow<PostEntity?> {
        return database.realm.query<PostEntity>("postId = $0", postId).first().asFlow().map {
            val singlePost: PostEntity? = it.obj
            singlePost
        }
    }

    override fun getAllPostsAsFlow(): Flow<List<PostEntity>> {
        return database.realm.query<PostEntity>().asFlow().map { result ->
            result.list.ifEmpty {
                createLocalData()
            }
        }
    }

    override fun findUserPostsFlow(user: UserEntity?): Flow<List<PostEntity>> {
        return user?.run {
            database.realm.query<PostEntity>("user.id = $0", id).asFlow().map { result ->
                result.list
            }
        } ?: flowOf(emptyList())
    }

    override fun addUserPostAsFlow(): Flow<PostEntity> {
        return flow {
            val currentUser = database.getCurrentUser()
            val singlePostEntity = PostEntity.createRandomWithoutUser()
            val createdPost = database.realm.writeBlocking {
                val addedPost = copyToRealm(singlePostEntity)
                findLatest(addedPost)?.user = findLatest(currentUser)
                addedPost
            }
            emit(createdPost)
        }
    }

    override fun updatePostLike(postId: String): SResult<Boolean> {
        return SResult.Success(
            database.realm.writeBlocking {
                database.realm.query<PostEntity>("postId = $0", postId).first().find()
                    ?.let { post ->
                        findLatest(post)?.updateLike()
                    } ?: false
            }
        )
    }

    private fun createLocalData(): List<PostEntity> {
        val createdData = PostEntity.createRandomList()
        createdData.forEach { entity ->
            database.realm.writeBlocking {
                copyToRealm(entity)
            }
        }
        return createdData
    }
}

interface IPostsLocalDataSource {
    fun getAllPostsAsFlow(): Flow<List<PostEntity>>
    fun findUserPostsFlow(user: UserEntity?): Flow<List<PostEntity>>
    fun addUserPostAsFlow(): Flow<PostEntity>
    fun findPostByIdAsFlow(postId: String): Flow<PostEntity?>

    fun updatePostLike(postId: String): SResult<Boolean>
}