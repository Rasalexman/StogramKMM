package ru.stogram.android.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.toSuccessListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.constants.StoriesResult
import ru.stogram.android.di.ModuleNames
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

@BindSingle(
    toClass = HomeViewModel::class,
    toModule = ModuleNames.ViewModels
)
class HomeViewModel : ViewModel() {

    val postsState: StateFlow<PostsResult> = flow {
        val posts = PostEntity.createRandomList()
        val data = posts.toSuccessListResult()
        logg { "posts counts = ${posts.size}" }
        emit(data)
    }.flowOn(Dispatchers.Default).asState(viewModelScope, emptyResult())

    val storiesState: StateFlow<StoriesResult> = flow {
        val data = UserEntity.createRandomList(true)
        val result = data.toSuccessListResult()
        logg { "stories counts = ${data.size}" }
        emit(result)
    }.flowOn(Dispatchers.Default).asState(viewModelScope, emptyResult())

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }
}