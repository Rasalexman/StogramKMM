package ru.stogram.android.features.profile

import androidx.lifecycle.ViewModel
import com.rasalexman.kodi.annotations.BindSingle
import ru.stogram.android.di.ModuleNames

@BindSingle(
    toClass = ProfileViewModel::class,
    toModule = ModuleNames.ViewModels
)
class ProfileViewModel : ViewModel() {

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }
}