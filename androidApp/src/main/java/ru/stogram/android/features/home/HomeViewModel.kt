package ru.stogram.android.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.kodi.core.instance
import com.rasalexman.sresult.common.extensions.*
import com.rasalexman.sresult.data.dto.SResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.constants.ScreenNames
import ru.stogram.android.di.ModuleNames
import ru.stogram.android.navigation.toUserProfile
import ru.stogram.models.PostEntity
import ru.stogram.repository.IPostsRepository
import ru.stogram.repository.IUserStoriesRepository

@BindSingle(
    toClass = HomeViewModel::class,
    toModule = ModuleNames.ViewModels
)
class HomeViewModel : ViewModel(), IKodi {

    private val postsRepository: IPostsRepository by immutableInstance()
    private val userStoriesRepository: IUserStoriesRepository by immutableInstance()

    val homeState: StateFlow<SResult<HomeState>> = combine(
        postsRepository.allPostsAsFlowable(),
        userStoriesRepository.getAllStoriesAsFlow()
    ) { posts, stories ->
        HomeState(posts, stories).toSuccessResult()
    }.flowOn(Dispatchers.IO).onStart {
        emit(loadingResult())
    }.asState(viewModelScope, emptyResult())

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }

    fun onPostAvatarClicked(post: PostEntity) {
        val postUser = post.takePostUser()
        logg { "Selected user name: ${postUser.name} | id: ${postUser.id}" }
        instance<NavHostController>().toUserProfile(postUser.id)
    }
}