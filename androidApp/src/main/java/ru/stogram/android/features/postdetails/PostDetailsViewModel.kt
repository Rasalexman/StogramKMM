package ru.stogram.android.features.postdetails

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
import ru.stogram.android.di.ModuleNames
import ru.stogram.models.PostEntity
import ru.stogram.repository.IPostsRepository

@BindSingle(
    toClass = PostDetailsViewModel::class,
    toModule = ModuleNames.ViewModels
)
class PostDetailsViewModel : ViewModel(), IKodi {

    private val postsRepository: IPostsRepository by immutableInstance()
    private val lastSelectedPostId: MutableStateFlow<String?> = MutableStateFlow(null)

    val postState: StateFlow<SResult<PostEntity>> = lastSelectedPostId.mapNotNull { it }.flatMapLatest {
        postsRepository.findPostByIdAsFlow(it).mapNotNull { currentPost ->
            currentPost.toSuccessResult()
        }
    }.asState(viewModelScope, emptyResult())

    fun fetchSelectedPost(selectedPostId: String?) {
        logg { "fetchSelectedPost id: ${selectedPostId.orEmpty()}" }
        selectedPostId?.let(lastSelectedPostId::tryEmit)
    }

    fun onBackClicked() {
        instance<NavHostController>().popBackStack()
    }
}