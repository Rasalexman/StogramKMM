package ru.stogram.android.features.postdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.CommentEntity
import ru.stogram.models.PostEntity
import ru.stogram.repository.IPostsRepository
import javax.inject.Inject


@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val router: IHostRouter,
    postsRepository: IPostsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postId: String = checkNotNull(savedStateHandle[ArgsNames.POST_ID])

    val postState: StateFlow<SResult<PostEntity>> =
        postsRepository.findPostByIdAsFlow(postId).mapNotNull { currentPost ->
            currentPost.toSuccessResult()
        }.asState(viewModelScope, emptyResult())

    fun onAvatarClicked(comment: CommentEntity) {
        router.showHostUserProfile(comment.takeCommentUser().id)
    }

    fun onBackClicked() {
        router.popBackToHost()
    }
}