package ru.stogram.android.features.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.toSuccessListResult
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.common.utils.convertList
import com.rasalexman.sresult.data.dto.SResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.constants.UserResult
import ru.stogram.android.features.base.BaseViewModel
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.IUser
import ru.stogram.models.UserEntity
import ru.stogram.repository.IPostsRepository
import ru.stogram.repository.IUserRepository
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val router: IHostRouter,
    private val postsRepository: IPostsRepository,
    private val postItemUIMapper: IPostItemUIMapper,
    private val userRepository: IUserRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val userId: String = checkNotNull(savedStateHandle[ArgsNames.USER_ID])
    //
    val login: String = checkNotNull(savedStateHandle[ArgsNames.USER_LOGIN])

    var topBarOffset = 0f

    private val userFlow: StateFlow<UserEntity?> = userRepository.findUserDetailsAsFlow(userId)
        .flowIoState(defaultValue = null)

    val showTopBar: StateFlow<Boolean> = userFlow.map {
        it?.isCurrentUser == false
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val userState: StateFlow<UserResult> = userFlow.mapNotNull { it }.map {
        it.toSuccessResult()
    }.flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyResult())

    val postsState: StateFlow<PostsResult> = userFlow.mapNotNull { it }.flatMapIoState { user ->
        postsRepository.findUserPostsAsFlow(user).map { posts ->
            postItemUIMapper.convertList(posts).toSuccessListResult()
        }
    }

    val postsCountState: StateFlow<SResult<String>> = postsState.mapIoState {
        it.data.orEmpty().size.toString().toSuccessResult()
    }

    fun onPostClicked(post: PostItemUI) = launchOnMain {
        router.showHostPostDetails(post.postId, true)
    }

    fun onProfileButtonClicked(user: IUser) = launchOnMain {
        if(user.isCurrentUser) {
            router.showHostLoginScreen()
        } else {
            val localUserId = user.id
            if(!user.isSubscribed) {
                userRepository.subscribeToUser(localUserId)
            } else {
                userRepository.unsubscribeToUser(localUserId)
            }
        }
    }

    fun onScreenTypeClick(screenType: String) {
        val currentUserId = userId.takeIf { it.isNotEmpty() }
        router.showSubsAndObserversScreen(userId = currentUserId, screenType = screenType)
    }

    fun onBackClicked() = launchOnMain {
        router.popBackToHost()
    }
}