package ru.stogram.android.features.main

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.stogram.android.navigation.IMainRouter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val router: IMainRouter
) : ViewModel() {

    fun setupMainNavController(navigator: NavHostController) {
        router.setupMainNavHostController(navigator)
    }
}