package ru.stogram.android.features.comments

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.*
import com.rasalexman.sresult.common.utils.convertList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.constants.CommentsResult
import ru.stogram.android.features.base.BaseViewModel
import ru.stogram.android.mappers.ICommentItemUIMapper
import ru.stogram.android.models.CommentItemUI
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.IUser
import ru.stogram.models.ReactionEntity
import ru.stogram.repository.ICommentsRepository
import ru.stogram.repository.IReactionsRepository
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val router: IHostRouter,
    private val commentItemUIMapper: ICommentItemUIMapper,
    private val reactionRepository: IReactionsRepository,
    private val commentsRepository: ICommentsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val inputComment: MutableStateFlow<String> = MutableStateFlow("")
    private val lastSelectedPostId: String = checkNotNull(savedStateHandle[ArgsNames.POST_ID])

    val commentsState: StateFlow<CommentsResult> = commentsRepository.getAllCommentsAsFlow(lastSelectedPostId)
        .map { currentComments ->
            commentItemUIMapper.convertList(currentComments).toSuccessResult()
    }.flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.Eagerly, loadingResult())

    fun onAvatarClicked(commentUser: IUser) = launchOnMain {
        router.showHostUserProfile(commentUser.id)
    }

    fun onLikeClicked(comment: CommentItemUI) = launchOnMain {
        val updateResult = withContext(Dispatchers.IO) {
            commentsRepository.updateCommentLike(comment.id)
        }
        logg { "onPostLikeClicked result ${updateResult.data.orFalse()}" }
    }

    fun onDoneCommentHandler() = launchOnMain {
        val inputCommentText = inputComment.value
        if(inputCommentText.isNotEmpty()) {
            val commentResult = withContext(Dispatchers.IO) {
                commentsRepository.addCommentToPost(lastSelectedPostId, inputCommentText).flatMapIfSuccessSuspend {
                    reactionRepository.createReaction(ReactionEntity.photoComment, lastSelectedPostId, inputCommentText)
                }
            }.applyIfSuccessSuspend {
                inputComment.emit("")
            }
            logg { "onDoneCommentHandler result ${commentResult.data.orFalse()}" }
        }
    }

    fun onBackClicked() = launchOnMain {
        router.popBackToHost()
    }
}