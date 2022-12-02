package ru.stogram.android.features.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.*
import com.rasalexman.sresult.common.utils.convertList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.constants.UserResult
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
    userRepository: IUserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val profileId: String = checkNotNull(savedStateHandle[ArgsNames.USER_ID])

    val showTopBar: Boolean = profileId != UserEntity.DEFAULT_USER_ID
    var topBarOffset = 0f

    private val userFlow: MutableStateFlow<IUser?> = MutableStateFlow(null)

    val userState: StateFlow<UserResult> = userFlow.mapNotNull {
        it.toSuccessResult()
    }.flowOn(Dispatchers.IO).asState(viewModelScope, emptyResult())

    @OptIn(FlowPreview::class)
    val postsState: StateFlow<PostsResult> = userRepository.findUserDetailsAsFlow(profileId)
        .mapNotNull {
            it
        }.flatMapConcat { user ->
            userFlow.emit(user)
            postsRepository.findUserPostsAsFlow(user).map { posts ->
                postItemUIMapper.convertList(posts).toSuccessListResult()
            }
        }.flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.Eagerly, loadingResult())

    fun onPostClicked(post: PostItemUI) {
        router.showHostPostDetails(post.postId)
    }

    fun onBackClicked() {
        router.popBackToHost()
    }
}