package ru.stogram.android.features.login

import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.applyIfError
import com.rasalexman.sresult.common.extensions.applyIfSuccessSuspend
import com.rasalexman.sresult.common.extensions.loggE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.stogram.android.features.base.BaseViewModel
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.UserEntity
import ru.stogram.repository.IUserRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val router: IHostRouter,
    private val userRepository: IUserRepository
) : BaseViewModel() {

    val login: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")

    val currentUser = userRepository.getCurrentUserFlow().onEach { user ->
        login.emit(user.login)
        password.emit(user.password)
    }.flowOn(Dispatchers.IO).launchIn(viewModelScope)

    fun onSignInClicked() = launchOnMain {
        if(login.value.isNotEmpty() && password.value.isNotEmpty()) {
            withContext(Dispatchers.IO) {
                userRepository.authUser(login.value, password.value)
            }.applyIfSuccessSuspend {
                router.showHostMainScreen()
            }.applyIfError {
                loggE { "ERROR REGISTER USER ${it.message}" }
                onRegisterClicked()
            }
        }
    }

    fun onRegisterClicked() = launchOnMain {
        router.showHostRegisterScreen()
    }
}