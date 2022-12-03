package ru.stogram.repository

import com.rasalexman.sresult.data.dto.SResult
import kotlinx.coroutines.flow.Flow
import ru.stogram.database.CFlow
import ru.stogram.database.wrap
import ru.stogram.models.ReactionEntity
import ru.stogram.sources.local.IReactionsLocalDataSource

class ReactionsRepository(
    private val localDataSource: IReactionsLocalDataSource
) : IReactionsRepository {

    override fun getAllReactionsAsCommonFlow(): CFlow<List<ReactionEntity>> {
        return getAllReactionsAsFlow().wrap()
    }

    override fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>> {
        return localDataSource.getAllReactionsAsFlow()
    }

    override fun createReaction(reactionType: String, postId: String, postComment: String): SResult<Boolean> {
        return localDataSource.createReaction(reactionType, postId, postComment)
    }

    override fun update() {
        localDataSource.update()
    }
}

interface IReactionsRepository {
    fun getAllReactionsAsCommonFlow(): CFlow<List<ReactionEntity>>
    fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>>
    fun createReaction(reactionType: String, postId: String, postComment: String = ""): SResult<Boolean>

    fun update()
}