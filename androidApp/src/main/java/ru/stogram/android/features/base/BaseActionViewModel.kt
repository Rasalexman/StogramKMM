package ru.stogram.android.features.base

import ru.stogram.android.navigation.IHostRouter
import ru.stogram.models.IUser

abstract class BaseActionViewModel : BaseViewModel() {

    abstract val router: IHostRouter

    open fun onAvatarClicked(commentUser: IUser) = launchOnMain {
        router.showHostUserProfile(commentUser)
    }

    open fun onBackClicked() = launchOnMain {
        router.popBackToHost()
    }
}