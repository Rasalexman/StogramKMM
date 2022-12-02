package ru.stogram.android.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.toSuccessListResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.PostEntity
import ru.stogram.repository.ISearchRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val router: IHostRouter,
    private val searchRepository: ISearchRepository
) : ViewModel() {

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

    fun onPostClicked(post: PostEntity) {
        router.showHostPostDetails(post.postId)
    }
}