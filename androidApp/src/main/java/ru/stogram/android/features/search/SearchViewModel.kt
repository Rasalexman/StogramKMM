package ru.stogram.android.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.toSuccessListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.di.ModuleNames
import ru.stogram.models.PostEntity

@BindSingle(
    toClass = SearchViewModel::class,
    toModule = ModuleNames.ViewModels
)
class SearchViewModel : ViewModel() {

    private val defaultPosts by lazy {
        PostEntity.createRandomList() //emptyList<PostEntity>() //
    }

    private val defaultPostsFlow = flow<List<PostEntity>> {
        emit(defaultPosts)
    }

    private val currentSearchQuery = MutableStateFlow("")

    val searchQuery = MutableStateFlow("")

    val postsState: StateFlow<PostsResult> = combine(currentSearchQuery, defaultPostsFlow) { query, defaults ->
        if(query.isNotEmpty()) {
            val searchedPosts = PostEntity.createRandomList()
            searchedPosts.filter { it.takePostUser().name.lowercase().contains(query.lowercase()) }
        } else {
            defaults
        }.toSuccessListResult()
    }.flowOn(Dispatchers.Default).asState(viewModelScope, emptyResult())

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }

    fun onSearchButtonPressed() {
        logg { "---> SEARCH QUERY IS ${searchQuery.value}" }
        currentSearchQuery.value = searchQuery.value
    }
}