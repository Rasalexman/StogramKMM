package ru.stogram.android.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.orFalse
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.common.utils.convertList
import com.rasalexman.sresult.data.dto.SResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.repository.IPostsRepository
import ru.stogram.repository.IUserStoriesRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val router: IHostRouter,
    private val postsRepository: IPostsRepository,
    private val postItemUIMapper: IPostItemUIMapper,
    userStoriesRepository: IUserStoriesRepository
) : ViewModel() {


    val homeState: StateFlow<SResult<HomeState>> = combine(
        postsRepository.allPostsAsFlowable().map { posts->
            postItemUIMapper.convertList(posts)
        },
        userStoriesRepository.getAllStoriesAsFlow()
    ) { posts, stories ->
        HomeState(posts, stories).toSuccessResult()
    }.flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Eagerly, loadingResult())

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }

    fun onPostAvatarClicked(post: PostItemUI) {
        val postUser = post.user
        logg { "Selected user name: ${postUser.name} | id: ${postUser.id}" }
        router.showHostUserProfile(postUser.id)
    }

    fun onPostCommentsClicked(post: PostItemUI) {
        logg { "Selected post id: ${post.postId}" }
        router.showHostPostComments(post.postId)
    }

    fun onPostLikeClicked(post: PostItemUI) {
        viewModelScope.launch {
            val postResult = withContext(Dispatchers.IO) {
                postsRepository.updatePostLike(post.postId)
            }
            logg { "onPostLikeClicked result ${postResult.data.orFalse()}" }
        }
    }
}