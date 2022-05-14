package ru.stogram.android.features.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.toSuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.constants.UserResult
import ru.stogram.android.di.ModuleNames
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

@BindSingle(
    toClass = ProfileViewModel::class,
    toModule = ModuleNames.ViewModels
)
class ProfileViewModel : ViewModel() {

    private val defaultUser by lazy {
        UserEntity.createRandomDetailed(true)
    }

    private val defaultPosts by lazy {
        PostEntity.createRandomList() //emptyList<PostEntity>() //
    }

    val topBarOffset = mutableStateOf(0f)

    val userState: StateFlow<UserResult> = flow<UserResult> {
        emit(defaultUser.toSuccessResult())
    }.flowOn(Dispatchers.Default).asState(viewModelScope, emptyResult())

    val postsState: StateFlow<PostsResult> = flow<PostsResult> {
        emit(defaultPosts.toSuccessResult())
    }.flowOn(Dispatchers.Default).asState(viewModelScope, emptyResult())

}