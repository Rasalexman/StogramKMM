package ru.stogram.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import ru.stogram.database.CFlow
import ru.stogram.database.wrap
import ru.stogram.models.PostEntity

class SearchRepository : ISearchRepository {

    private var lastQuery: String = ""
    private var searchPosts = mutableListOf<PostEntity>()

    private val currentSearchQuery = MutableStateFlow("")
    private val defaultPostsFlow by lazy {
        createRandomPostsFlow()
    }

    private val searchedPosts: Flow<List<PostEntity>> = currentSearchQuery.flatMapLatest { query ->
        if(query.isNotEmpty()) {
            searchPosts(query)
        } else {
            defaultPostsFlow
        }
    }

    override fun takeSearchedPostsFlow(): Flow<List<PostEntity>> {
        return searchedPosts
    }

    override fun takeSearchedPostsCommonFlow(): CFlow<List<PostEntity>> {
        return takeSearchedPostsFlow().wrap()
    }

    override fun onQueryChanged(query: String) {
        currentSearchQuery.value = query
    }

    private fun searchPosts(query: String): Flow<List<PostEntity>> {
        return flow {
            val resultPosts = if (query != lastQuery) {
                lastQuery = query
                val lowerQuery = query.lowercase()
                val randomPosts = PostEntity.createRandomList()
                randomPosts.filter { post ->
                    post.takePostUser().name.contains(lowerQuery, true) ||
                            post.text.contains(lowerQuery, true)
                }.also {
                    searchPosts.apply {
                        clear()
                        addAll(it)
                    }
                }
            } else {
                searchPosts
            }
            emit(resultPosts)
        }
    }

    private fun createRandomPostsFlow(): Flow<List<PostEntity>> {
        return flow {
            emit(PostEntity.createRandomList())
        }
    }
}

interface ISearchRepository {
    fun onQueryChanged(query: String)
    fun takeSearchedPostsFlow(): Flow<List<PostEntity>>
    fun takeSearchedPostsCommonFlow(): CFlow<List<PostEntity>>
}