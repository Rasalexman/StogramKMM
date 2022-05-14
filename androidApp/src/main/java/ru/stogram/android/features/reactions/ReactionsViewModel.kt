package ru.stogram.android.features.reactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.toSuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.stogram.android.constants.ReactionsResult
import ru.stogram.android.di.ModuleNames
import ru.stogram.models.ReactionEntity

@BindSingle(
    toClass = ReactionsViewModel::class,
    toModule = ModuleNames.ViewModels
)
class ReactionsViewModel : ViewModel() {

    private val reactions by lazy {
        ReactionEntity.createRandomList()
    }

    val reactionsState: StateFlow<ReactionsResult> = flow<ReactionsResult> {
        emit(reactions.toSuccessResult())
    }.flowOn(Dispatchers.Default).asState(viewModelScope, emptyResult())

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }
}