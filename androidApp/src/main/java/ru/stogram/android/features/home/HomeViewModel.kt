package ru.stogram.android.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.*
import com.rasalexman.sresult.data.dto.SResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.PostEntity
import ru.stogram.repository.IPostsRepository
import ru.stogram.repository.IUserStoriesRepository
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val router: IHostRouter,
    postsRepository: IPostsRepository,
    userStoriesRepository: IUserStoriesRepository
) : ViewModel() {

    val homeState: StateFlow<SResult<HomeState>> = combine(
        postsRepository.allPostsAsFlowable(),
        userStoriesRepository.getAllStoriesAsFlow()
    ) { posts, stories ->
        HomeState(posts, stories).toSuccessResult()
    }.flowOn(Dispatchers.IO).onStart {
        emit(loadingResult())
    }.asState(viewModelScope, emptyResult())

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }

    fun onPostAvatarClicked(post: PostEntity) {
        val postUser = post.takePostUser()
        logg { "Selected user name: ${postUser.name} | id: ${postUser.id}" }
        router.showHostUserProfile(postUser.id)
    }

    fun onPostCommentsClicked(post: PostEntity) {
        logg { "Selected post id: ${post.postId}" }
        router.showHostPostComments(post.postId)
    }
}