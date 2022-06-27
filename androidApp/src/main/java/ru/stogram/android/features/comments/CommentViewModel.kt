package ru.stogram.android.features.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.kodi.core.instance
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import ru.stogram.android.constants.CommentsResult
import ru.stogram.android.di.ModuleNames
import ru.stogram.models.PostEntity
import ru.stogram.repository.ICommentsRepository

@BindSingle(
    toClass = CommentViewModel::class,
    toModule = ModuleNames.ViewModels
)
class CommentViewModel : ViewModel(), IKodi {

    private val commentsRepository: ICommentsRepository by immutableInstance()

    private val lastSelectedPostId: MutableStateFlow<String?> = MutableStateFlow(null)

    val commentsState: StateFlow<CommentsResult> = lastSelectedPostId.mapNotNull { it }.flatMapLatest { postId ->
        commentsRepository.getAllCommentsAsFlow(postId).mapNotNull { currentComments ->
            currentComments.toSuccessResult()
        }
    }.asState(viewModelScope, emptyResult())

    fun fetchComments(selectedPostId: String?) {
        logg { "fetchSelectedPost id: ${selectedPostId.orEmpty()}" }
        selectedPostId?.let(lastSelectedPostId::tryEmit)
    }

    fun onBackClicked() {
        instance<NavHostController>().popBackStack()
    }
}