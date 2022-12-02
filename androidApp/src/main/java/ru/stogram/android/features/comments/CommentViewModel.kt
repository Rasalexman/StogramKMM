package ru.stogram.android.features.comments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.orFalse
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.common.utils.convertList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.constants.CommentsResult
import ru.stogram.android.mappers.ICommentItemUIMapper
import ru.stogram.android.models.CommentItemUI
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.IUser
import ru.stogram.repository.ICommentsRepository
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val router: IHostRouter,
    private val commentItemUIMapper: ICommentItemUIMapper,
    private val commentsRepository: ICommentsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val lastSelectedPostId: String = checkNotNull(savedStateHandle[ArgsNames.POST_ID])

    val commentsState: StateFlow<CommentsResult> = commentsRepository.getAllCommentsAsFlow(lastSelectedPostId)
        .mapNotNull { currentComments ->
            commentItemUIMapper.convertList(currentComments).toSuccessResult()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, loadingResult())

    fun onAvatarClicked(commentUser: IUser) {
        router.showHostUserProfile(commentUser.id)
    }

    fun onLikeClicked(comment: CommentItemUI) {
        viewModelScope.launch {
            val updateResult = withContext(Dispatchers.IO) {
                commentsRepository.updateCommentLike(comment.id)
            }
            logg { "onPostLikeClicked result ${updateResult.data.orFalse()}" }
        }
    }

    fun onBackClicked() {
        router.popBackToHost()
    }
}