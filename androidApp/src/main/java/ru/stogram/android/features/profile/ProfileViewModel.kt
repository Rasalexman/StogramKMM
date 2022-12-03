package ru.stogram.android.features.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.toSuccessListResult
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.common.utils.convertList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.constants.UserResult
import ru.stogram.android.features.base.BaseViewModel
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.UserEntity
import ru.stogram.repository.IPostsRepository
import ru.stogram.repository.IUserRepository
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val router: IHostRouter,
    private val postsRepository: IPostsRepository,
    private val postItemUIMapper: IPostItemUIMapper,
    userRepository: IUserRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val profileId: String = checkNotNull(savedStateHandle[ArgsNames.USER_ID])

    var topBarOffset = 0f

    private val userFlow: StateFlow<UserEntity?> =
        userRepository.findUserDetailsAsFlow(profileId)
            .flowIoState(defaultValue = null)

    val showTopBar: StateFlow<Boolean> = userFlow.map {
        it?.isCurrentUser == false
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val userState: StateFlow<UserResult> = userFlow.mapNotNull {
        it.toSuccessResult()
    }.flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.Eagerly, emptyResult())

    val postsState: StateFlow<PostsResult> = userFlow.mapNotNull { it }.flatMapIoState { user ->
        postsRepository.findUserPostsAsFlow(user).map { posts ->
            postItemUIMapper.convertList(posts).toSuccessListResult()
        }
    }

    fun onPostClicked(post: PostItemUI) = launchOnMain {
        router.showHostPostDetails(post.postId, true)
    }

    fun onMessagesClicked() = launchOnMain {
        router.showHostLoginScreen()
    }

    fun onBackClicked() = launchOnMain {
        router.popBackToHost()
    }
}