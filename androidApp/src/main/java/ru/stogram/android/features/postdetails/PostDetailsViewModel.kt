package ru.stogram.android.features.postdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.orFalse
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.IUser
import ru.stogram.repository.IPostsRepository
import javax.inject.Inject


@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val router: IHostRouter,
    private val postItemUIMapper: IPostItemUIMapper,
    private val postsRepository: IPostsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postId: String = checkNotNull(savedStateHandle[ArgsNames.POST_ID])

    val postState: StateFlow<SResult<PostItemUI>> =
        postsRepository.findPostByIdAsFlow(postId).mapNotNull { currentPost ->
            currentPost
        }.map {
            postItemUIMapper.convertSingle(it).toSuccessResult()
        }.flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.Eagerly, loadingResult())

    fun onAvatarClicked(commentUser: IUser) {
        router.showHostUserProfile(commentUser.id)
    }

    fun onPostLikeClicked(post: PostItemUI) {
        viewModelScope.launch {
            val postResult = withContext(Dispatchers.IO) {
                postsRepository.updatePostLike(post.postId)
            }
            logg { "onPostLikeClicked result ${postResult.data.orFalse()}" }
        }
    }

    fun onBackClicked() {
        router.popBackToHost()
    }
}