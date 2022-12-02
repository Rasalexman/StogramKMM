package ru.stogram.repository

import com.rasalexman.sresult.data.dto.SResult
import kotlinx.coroutines.flow.Flow
import ru.stogram.database.CFlow
import ru.stogram.database.wrap
import ru.stogram.models.CommentEntity
import ru.stogram.sources.local.ICommentsLocalDataSource

class CommentsRepository(
    private val localDataSource: ICommentsLocalDataSource
) : ICommentsRepository {

    override fun getAllCommentsAsCommonFlow(postId: String): CFlow<List<CommentEntity>> {
        return getAllCommentsAsFlow(postId).wrap()
    }

    override fun getAllCommentsAsFlow(postId: String): Flow<List<CommentEntity>> {
        return localDataSource.getAllCommentsAsFlow(postId)
    }

    override fun updateCommentLike(commentId: String): SResult<Boolean> {
        return localDataSource.updateCommentLike(commentId)
    }
}

interface ICommentsRepository {
    fun getAllCommentsAsCommonFlow(postId: String): CFlow<List<CommentEntity>>
    fun getAllCommentsAsFlow(postId: String): Flow<List<CommentEntity>>
    fun updateCommentLike(commentId: String): SResult<Boolean>
}