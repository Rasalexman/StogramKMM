package ru.stogram.android.features.register

import com.rasalexman.sresult.common.extensions.applyIfError
import com.rasalexman.sresult.common.extensions.applyIfSuccessSuspend
import com.rasalexman.sresult.common.extensions.loggE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import ru.stogram.android.features.base.BaseViewModel
import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.UserEntity
import ru.stogram.repository.IUserRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val router: IHostRouter,
    private val userRepository: IUserRepository
) : BaseViewModel() {

    val randomUser = UserEntity.createRandomDetailed()

    val name: MutableStateFlow<String> = MutableStateFlow(randomUser.name)
    val login: MutableStateFlow<String> = MutableStateFlow(randomUser.login)
    val password: MutableStateFlow<String> = MutableStateFlow(randomUser.password)
    val passwordRepeat: MutableStateFlow<String> = MutableStateFlow(randomUser.password)

    fun onConfirmClicked() = launchOnMain {
        val userPassword = password.value
        val userName = name.value
        val userLogin = login.value
        val isValid = userName.isNotEmpty() && userLogin.isNotEmpty()
                && userPassword.isNotEmpty() && userPassword == passwordRepeat.value
        if (isValid) {
            randomUser.apply {
                password = userPassword
            }

            withContext(Dispatchers.IO) {
                userRepository.registerUser(randomUser)
            }.applyIfSuccessSuspend {
                router.showHostMainScreen()
            }.applyIfError {
                loggE { "ERROR REGISTER USER ${it.message}" }
                onBackClicked()
            }
        }
    }

    fun onBackClicked() = launchOnMain {
        router.popBackToHost()
    }
}