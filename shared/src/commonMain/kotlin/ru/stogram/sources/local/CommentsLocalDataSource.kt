package ru.stogram.sources.local

import com.rasalexman.sresult.data.dto.SResult
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.database.RealmDataBase
import ru.stogram.models.CommentEntity
import ru.stogram.models.PostEntity

class CommentsLocalDataSource(
    private val database: RealmDataBase
) : ICommentsLocalDataSource {

    override fun getAllCommentsAsFlow(postId: String): Flow<List<CommentEntity>> {
        return database.realm.query<CommentEntity>("postId = $0", postId).asFlow().map { result ->
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
}