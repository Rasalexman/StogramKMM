package ru.stogram.android.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.toSuccessListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.di.ModuleNames
import ru.stogram.repository.ISearchRepository

@BindSingle(
    toClass = SearchViewModel::class,
    toModule = ModuleNames.ViewModels
)
class SearchViewModel : ViewModel() {

    private val searchRepository: ISearchRepository by immutableInstance()

    val searchQuery = MutableStateFlow("")
    val refreshing: Boolean = false

    val postsState: StateFlow<PostsResult> = searchRepository.takeSearchedPostsFlow().map { posts ->
        posts.toSuccessListResult()
    }.flowOn(Dispatchers.Default).asState(viewModelScope, emptyResult())

    fun onSwipeRefresh() {

    }

    fun onSearchButtonPressed() {
        logg { "---> SEARCH QUERY IS ${searchQuery.value}" }
        searchRepository.onQueryChanged(searchQuery.value)
    }
}