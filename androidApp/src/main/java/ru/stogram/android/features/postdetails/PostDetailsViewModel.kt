package ru.stogram.android.features.postdetails

import androidx.lifecycle.SavedStateHandle
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.orFalse
import com.rasalexman.sresult.common.extensions.successResult
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.features.base.BaseActionViewModel
import ru.stogram.android.features.base.BaseViewModel
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.IUser
import ru.stogram.models.ReactionEntity
import ru.stogram.repository.IPostsRepository
import ru.stogram.repository.IReactionsRepository
import javax.inject.Inject


@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    override val router: IHostRouter,
    private val postItemUIMapper: IPostItemUIMapper,
    private val postsRepository: IPostsRepository,
    private val reactionRepository: IReactionsRepository,
    savedStateHandle: SavedStateHandle
) : BaseActionViewModel() {

    private val postId: String = checkNotNull(savedStateHandle[ArgsNames.POST_ID])
    private val fromProfile: Boolean = checkNotNull(savedStateHandle[ArgsNames.FROM_PROFILE])

    val postState: StateFlow<SResult<PostItemUI>> =
        postsRepository.findPostByIdAsFlow(postId).mapNotNull { currentPost ->
            currentPost
        }.mapIoState {
            postItemUIMapper.convertSingle(it).toSuccessResult()
        }

    fun onPostLikeClicked(post: PostItemUI) = launchOnMain {
        val postResult = withContext(Dispatchers.IO) {
            val localPostId = post.postId
            postsRepository.updatePostLike(localPostId).applyIfSuccess { isLiked ->
                if(isLiked) {
                    reactionRepository.createReaction(ReactionEntity.photoLike, localPostId)
                } else successResult(true)
            }
        }
        logg { "onPostLikeClicked result ${postResult.data.orFalse()}" }
    }

    fun onToolBarAvatarClicked(user: IUser) {
        if(fromProfile) {
            onBackClicked()
        } else {
            onAvatarClicked(user)
        }
    }
}