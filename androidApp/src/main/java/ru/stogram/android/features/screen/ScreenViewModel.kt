package ru.stogram.android.features.screen

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.stogram.android.navigation.IHostRouter
import javax.inject.Inject

@HiltViewModel
class ScreenViewModel @Inject constructor(
    private val router: IHostRouter
) : ViewModel() {

    fun setupNavHostController(navigator: NavHostController) {
        router.setupNavHostController(navigator)
    }
}