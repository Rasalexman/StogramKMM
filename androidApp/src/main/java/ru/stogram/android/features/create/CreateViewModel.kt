package ru.stogram.android.features.create

import androidx.lifecycle.ViewModel
import com.rasalexman.kodi.annotations.BindSingle
import ru.stogram.android.di.ModuleNames

@BindSingle(
    toClass = CreateViewModel::class,
    toModule = ModuleNames.ViewModels
)
class CreateViewModel : ViewModel() {

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }
}