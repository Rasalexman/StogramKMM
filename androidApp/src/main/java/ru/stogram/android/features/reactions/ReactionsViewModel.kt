package ru.stogram.android.features.reactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.kodi.core.IKodi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.kodi.core.instance
import com.rasalexman.sresult.common.extensions.asState
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.extensions.toSuccessListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ru.stogram.android.constants.ReactionsResult
import ru.stogram.android.di.ModuleNames
import ru.stogram.android.navigation.toUserProfile
import ru.stogram.models.ReactionEntity
import ru.stogram.repository.IReactionsRepository

@BindSingle(
    toClass = ReactionsViewModel::class,
    toModule = ModuleNames.ViewModels
)
class ReactionsViewModel : ViewModel(), IKodi {

    val refreshing: Boolean = false

    private val reactionsRepository: IReactionsRepository by immutableInstance()

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
        instance<NavHostController>().toUserProfile(user.id)
    }
}