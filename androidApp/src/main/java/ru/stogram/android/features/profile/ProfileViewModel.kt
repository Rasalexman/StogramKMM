package ru.stogram.android.features.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.kodi.core.instance
import com.rasalexman.sresult.common.extensions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.constants.UserResult
import ru.stogram.android.di.ModuleNames
import ru.stogram.android.navigation.toPostDetails
import ru.stogram.models.IUser
import ru.stogram.models.PostEntity
import ru.stogram.repository.IPostsRepository
import ru.stogram.repository.IUserRepository

@BindSingle(
    toClass = ProfileViewModel::class,
    toModule = ModuleNames.ViewModels
)
class ProfileViewModel : ViewModel(), IKodi {

    private val userRepository: IUserRepository by immutableInstance()
    private val postsRepository: IPostsRepository by immutableInstance()

    private val defaultPosts by lazy {
        PostEntity.createRandomList().toSuccessResult()
    }

    private val lastProfileId: MutableStateFlow<String> = MutableStateFlow("")
    val topBarOffset = mutableStateOf(0f)

    private val userFlow: MutableStateFlow<IUser?> = MutableStateFlow(null)

    val userState: StateFlow<UserResult> = userFlow.mapNotNull {
        it.toSuccessResult()
    }.flowOn(Dispatchers.IO).asState(viewModelScope, emptyResult())

    val postsState: StateFlow<PostsResult> = lastProfileId.flatMapLatest { userId ->
        logg { "lastProfileId = $userId" }
        userRepository.findUserDetailsAsFlow(userId).mapNotNull {
            it
        }.flatMapConcat { user ->
            userFlow.tryEmit(user)
            postsRepository.findUserPostsAsFlow(user).map { posts ->
                posts.toSuccessListResult()
            }
        }
    }.flowOn(Dispatchers.IO).asState(viewModelScope, emptyResult())

    fun fetchUserProfile(userId: String?) {
        logg { "fetchUserProfile = $userId" }
        userId?.let(lastProfileId::tryEmit)
    }

    fun onPostClicked(post: PostEntity) {
        instance<NavHostController>().toPostDetails(post.postId)
    }

    fun onBackClicked() {
        instance<NavHostController>().popBackStack()
    }



}