package ru.stogram.android.features.home

import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.flatMapIfSuccessSuspend
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.orFalse
import com.rasalexman.sresult.common.extensions.successResult
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.common.utils.convertList
import com.rasalexman.sresult.data.dto.SResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import ru.stogram.android.features.base.BaseActionViewModel
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.ReactionEntity
import ru.stogram.repository.IPostsRepository
import ru.stogram.repository.IReactionsRepository
import ru.stogram.repository.IUserStoriesRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    override val router: IHostRouter,
    private val postsRepository: IPostsRepository,
    private val reactionRepository: IReactionsRepository,
    private val postItemUIMapper: IPostItemUIMapper,
    userStoriesRepository: IUserStoriesRepository
) : BaseActionViewModel() {


    val homeState: StateFlow<SResult<HomeState>> = combine(
        postsRepository.allPostsAsFlowable().map { posts->
            postItemUIMapper.convertList(posts)
        },
        userStoriesRepository.getAllStoriesAsFlow(),
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
        onAvatarClicked(postUser)
    }

    fun onPostCommentsClicked(post: PostItemUI) = launchOnMain{
        logg { "Selected post id: ${post.postId}" }
        router.showHostPostComments(post.postId)
    }

    fun onPostLikeClicked(post: PostItemUI) = launchOnMain {
        val postResult = withContext(Dispatchers.IO) {
            val localPostId = post.postId
            postsRepository.updatePostLike(post.postId).flatMapIfSuccessSuspend { isLiked ->
                if(isLiked) {
                    reactionRepository.createReaction(ReactionEntity.photoLike, localPostId)
                } else successResult(true)
            }
        }
        logg { "onPostLikeClicked result ${postResult.data.orFalse()}" }
    }
}