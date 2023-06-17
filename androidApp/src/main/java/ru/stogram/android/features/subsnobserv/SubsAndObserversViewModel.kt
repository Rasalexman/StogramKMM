package ru.stogram.android.features.subsnobserv

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.asState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import ru.stogram.android.constants.ArgsNames
import ru.stogram.android.constants.ScreenType
import ru.stogram.android.constants.SubsNObsResult
import ru.stogram.android.features.base.BaseActionViewModel
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.repository.IUserRepository
import javax.inject.Inject

@HiltViewModel
class SubsAndObserversViewModel @Inject constructor(
    override val router: IHostRouter,
    private val userRepository: IUserRepository,
    savedStateHandle: SavedStateHandle
) : BaseActionViewModel() {

    val screenType: String = savedStateHandle.get<String>(ArgsNames.SCREEN_TYPE).orEmpty()

    private val userId: StateFlow<String> = savedStateHandle.getStateFlow(ArgsNames.USER_ID, "").map {
        it.takeIf { it != "null" }.orEmpty()
    }.asState(viewModelScope, "")

    val subsNObservState: StateFlow<SubsNObsResult> = userId.flatMapIoState { currentUserId ->
        if(screenType == ScreenType.OBSERVE) userRepository.getUserObservers(currentUserId)
        else userRepository.getUserSubscribers(currentUserId)
    }

}