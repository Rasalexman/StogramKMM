package ru.stogram.android.features.reactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.extensions.toSuccessListResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ru.stogram.android.constants.ReactionsResult
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.ReactionEntity
import ru.stogram.repository.IReactionsRepository
import javax.inject.Inject


@HiltViewModel
class ReactionsViewModel @Inject constructor(
    private val router: IHostRouter,
    private val reactionsRepository: IReactionsRepository
) : ViewModel() {

    val refreshing: Boolean = false

    val reactionsState: StateFlow<ReactionsResult> by lazy {
        reactionsRepository.getAllReactionsAsFlow().map { result ->
            result.toSuccessListResult()
        }.onStart {
            emit(loadingResult())
        }.flowOn(Dispatchers.IO).asState(viewModelScope, emptyResult())
    }

    fun onSwipeRefresh() {

    }

    fun onReactionAvatarClicked(reaction: ReactionEntity) {
        val user = reaction.takeUserFrom()
        router.showHostUserProfile(user.id)
    }
}