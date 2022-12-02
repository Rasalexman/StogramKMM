package ru.stogram.android.features.comments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.toSuccessResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.constants.CommentsResult
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.CommentEntity
import ru.stogram.repository.ICommentsRepository
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val router: IHostRouter,
    commentsRepository: ICommentsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val lastSelectedPostId: String = checkNotNull(savedStateHandle[ArgsNames.POST_ID])

    val commentsState: StateFlow<CommentsResult> = commentsRepository.getAllCommentsAsFlow(lastSelectedPostId)
        .mapNotNull { currentComments ->
        currentComments.toSuccessResult()
    }.asState(viewModelScope, emptyResult())

    fun onAvatarClicked(comment: CommentEntity) {
        router.showHostUserProfile(comment.takeCommentUser().id)
    }

    fun onBackClicked() {
        router.popBackToHost()
    }
}