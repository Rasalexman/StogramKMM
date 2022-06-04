package ru.stogram.android.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.toSuccessListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.constants.StoriesResult
import ru.stogram.android.di.ModuleNames
import ru.stogram.repository.IPostsRepository
import ru.stogram.repository.IUserStoriesRepository

@BindSingle(
    toClass = HomeViewModel::class,
    toModule = ModuleNames.ViewModels
)
class HomeViewModel : ViewModel() {

    private val postsRepository: IPostsRepository by immutableInstance()
    private val userStoriesRepository: IUserStoriesRepository by immutableInstance()

    val postsState: StateFlow<PostsResult> = postsRepository.allPostsAsFlowable().map { posts ->
        posts.toSuccessListResult()
    }.flowOn(Dispatchers.IO).asState(viewModelScope, emptyResult())

    val storiesState: StateFlow<StoriesResult> = userStoriesRepository.getAllStoriesAsFlow().map { stories ->
        stories.toSuccessListResult()
    }.flowOn(Dispatchers.Default).asState(viewModelScope, emptyResult())

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }
}