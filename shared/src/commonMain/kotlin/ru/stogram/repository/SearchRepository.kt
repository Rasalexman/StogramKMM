package ru.stogram.repository

import kotlinx.coroutines.flow.Flow
import ru.stogram.database.CFlow
import ru.stogram.database.wrap
import ru.stogram.models.PostEntity
import ru.stogram.sources.local.ISearchLocalDataSource

class SearchRepository(
    private val localDataSource: ISearchLocalDataSource
) : ISearchRepository {

    override fun takeSearchedPostsFlow(): Flow<List<PostEntity>> {
        return localDataSource.observeSearchResult()
    }

    override fun takeSearchedPostsCommonFlow(): CFlow<List<PostEntity>> {
        return takeSearchedPostsFlow().wrap()
    }

    override fun onQueryChanged(query: String) {
        localDataSource.onQueryChanged(query)
    }
}

interface ISearchRepository {
    fun onQueryChanged(query: String)
    fun takeSearchedPostsFlow(): Flow<List<PostEntity>>
    fun takeSearchedPostsCommonFlow(): CFlow<List<PostEntity>>
}