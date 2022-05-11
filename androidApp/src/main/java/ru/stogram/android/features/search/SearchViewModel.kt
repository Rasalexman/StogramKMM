package ru.stogram.android.features.search

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
import ru.stogram.android.di.ModuleNames
import ru.stogram.models.PostEntity

@BindSingle(
    toClass = SearchViewModel::class,
    toModule = ModuleNames.ViewModels
)
class SearchViewModel : ViewModel() {

    val postsState: StateFlow<PostsResult> = flow {
        val posts = PostEntity.createRandomList()
        val data = posts.toSuccessListResult()
        logg { "posts counts = ${posts.size}" }
        emit(data)
    }.flowOn(Dispatchers.Default).asState(viewModelScope, emptyResult())

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }
}