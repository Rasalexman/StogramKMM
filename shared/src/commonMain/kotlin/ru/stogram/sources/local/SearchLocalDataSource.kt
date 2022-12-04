package ru.stogram.sources.local

import io.realm.kotlin.ext.query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.stogram.database.RealmDataBase
import ru.stogram.models.PostEntity

class SearchLocalDataSource(
    private val dataBase: RealmDataBase
) : ISearchLocalDataSource {

    private var lastQuery: String = ""
    private val currentSearchQuery = MutableStateFlow("")

    override fun onQueryChanged(query: String) {
        currentSearchQuery.value = query
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeSearchResult(): Flow<List<PostEntity>> {
        return currentSearchQuery.flatMapLatest { query ->
            if (query.isNotEmpty() && query != lastQuery) {
                lastQuery = query
                val lowerQuery = query.lowercase()
                dataBase.realm.query<PostEntity>(
                    "text CONTAINS[c] $0 OR user.name CONTAINS[c] $0",
                    lowerQuery
                )
                    .asFlow().map { postsResult ->
                        val queryResult: List<PostEntity> = postsResult.list
                        queryResult
                    }

            } else {
                lastQuery = ""
                dataBase.realm.query<PostEntity>().limit(20).asFlow().map { postsResult ->
                    val queryResult: List<PostEntity> = postsResult.list
                    queryResult
                }
            }
        }
    }
}

interface ISearchLocalDataSource {
    fun observeSearchResult(): Flow<List<PostEntity>>
    fun onQueryChanged(query: String)
}