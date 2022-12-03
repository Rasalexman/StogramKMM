package ru.stogram.sources.local

import com.rasalexman.sresult.data.dto.SResult
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import ru.stogram.database.RealmDataBase
import ru.stogram.models.CommentEntity
import ru.stogram.models.UserEntity
import ru.stogram.utils.getRandomString

class CommentsLocalDataSource(
    private val database: RealmDataBase
) : ICommentsLocalDataSource {

    override fun getAllCommentsAsFlow(postId: String): Flow<List<CommentEntity>> {
        return database.realm.query<CommentEntity>("postId = $0", postId)
            .sort("createdAt", Sort.DESCENDING).asFlow().map { result ->
                result.list.ifEmpty {
                    createLocalData(postId)
                }
            }
    }

    override fun updateCommentLike(commentId: String): SResult<Boolean> {
        return SResult.Success(
            database.realm.writeBlocking {
                database.realm.query<CommentEntity>("id = $0", commentId).first().find()
                    ?.let { comment ->
                        findLatest(comment)?.updateLike()
                    } ?: false
            }
        )
    }

    override fun addCommentToPost(commentPostId: String, commentText: String): SResult<Boolean> {
        val currentUser: UserEntity = database.getCurrentUser()
        val newComment = CommentEntity().apply {
            id = getRandomString(100)
            postId = commentPostId
            text = commentText
            createdAt = Clock.System.now().epochSeconds
        }
        database.realm.writeBlocking {
            findLatest(currentUser)?.let { userEntity ->
                newComment.user = userEntity
                copyToRealm(newComment)
            }
        }
        return SResult.Success(true)
    }

    private fun createLocalData(postId: String): List<CommentEntity> {
        val createdData = CommentEntity.createRandomList(postId)
        createdData.forEach { entity ->
            database.realm.writeBlocking {
                copyToRealm(entity)
            }
        }
        return createdData
    }


}

interface ICommentsLocalDataSource {
    fun getAllCommentsAsFlow(postId: String): Flow<List<CommentEntity>>
    fun updateCommentLike(commentId: String): SResult<Boolean>
    fun addCommentToPost(commentPostId: String, commentText: String): SResult<Boolean>
}