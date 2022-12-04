package ru.stogram.android.features.reactions

import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.toSuccessListResult
import com.rasalexman.sresult.common.utils.convertList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val reactionsState: StateFlow<ReactionsResult> = reactionsRepository.getAllReactionsAsFlow()
        .mapIoState { result ->
            reactionItemUIMapper.convertList(result).toSuccessListResult()
        }

    fun onSwipeRefresh() = launchOnMain {
        logg { "onSwipeRefresh ${isRefreshing.value}" }
        reactionsRepository.update()
    }

    fun onPostClicked(post: PostItemUI) = launchOnMain {
        router.showHostPostDetails(post.postId, false)
    }

    fun onAvatarClicked(reactionUser: IUser) = launchOnMain {
        router.showHostUserProfile(reactionUser.id)
    }
}