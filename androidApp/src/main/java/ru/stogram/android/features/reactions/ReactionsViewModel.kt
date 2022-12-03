package ru.stogram.android.features.reactions

import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.extensions.toSuccessListResult
import com.rasalexman.sresult.common.utils.convertList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.stogram.android.constants.ReactionsResult
import ru.stogram.android.features.base.BaseViewModel
import ru.stogram.android.mappers.IReactionItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.IUser
import ru.stogram.repository.IReactionsRepository
import javax.inject.Inject


@HiltViewModel
class ReactionsViewModel @Inject constructor(
    private val router: IHostRouter,
    private val reactionsRepository: IReactionsRepository,
    private val reactionItemUIMapper: IReactionItemUIMapper
) : BaseViewModel() {

    val refreshing: Boolean = false

    val reactionsState: StateFlow<ReactionsResult> by lazy {
        reactionsRepository.getAllReactionsAsFlow().map { result ->
            reactionItemUIMapper.convertList(result).toSuccessListResult()
        }.flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.Eagerly, loadingResult())
    }

    fun onSwipeRefresh() {

    }

    fun onPostClicked(post: PostItemUI) = launchOnMain {
        router.showHostPostDetails(post.postId)
    }

    fun onAvatarClicked(reactionUser: IUser) = launchOnMain {
        router.showHostUserProfile(reactionUser.id)
    }
}