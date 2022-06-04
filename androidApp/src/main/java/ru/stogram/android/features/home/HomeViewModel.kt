package ru.stogram.android.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
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

    val homeState: StateFlow<SResult<HomeState>> = combine(
        postsRepository.allPostsAsFlowable(),
        userStoriesRepository.getAllStoriesAsFlow()
    ) { posts, stories ->
        HomeState(posts, stories).toSuccessResult()
    }.onStart {
        emit(loadingResult())
    }.flowOn(Dispatchers.IO).asState(viewModelScope, emptyResult())

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }
}