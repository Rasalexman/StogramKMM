package ru.stogram.android.features.search

import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.common.utils.convertList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.features.base.BaseViewModel
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.repository.ISearchRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val router: IHostRouter,
    private val searchRepository: ISearchRepository,
    private val postItemUIMapper: IPostItemUIMapper,
) : BaseViewModel() {

    val searchQuery = MutableStateFlow("")
    val refreshing: Boolean = false

    val postsState: StateFlow<PostsResult> = searchRepository.takeSearchedPostsFlow().map { posts ->
        postItemUIMapper.convertList(posts).toSuccessResult()
    }.flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.Eagerly, loadingResult())

    fun onSwipeRefresh() {

    }

    fun onSearchButtonPressed() = launchOnMain {
        logg { "---> SEARCH QUERY IS ${searchQuery.value}" }
        searchRepository.onQueryChanged(searchQuery.value)
    }

    fun onPostClicked(post: PostItemUI) = launchOnMain {
        router.showHostPostDetails(post.postId)
    }
}