package ru.stogram.repository

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
}

interface IReactionsRepository {
    fun getAllReactionsAsCommonFlow(): CFlow<List<ReactionEntity>>
    fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>>
}