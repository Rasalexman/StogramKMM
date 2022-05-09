package ru.stogram.android.features.search

import androidx.lifecycle.ViewModel
import com.rasalexman.kodi.annotations.BindSingle
import ru.stogram.android.di.ModuleNames

@BindSingle(
    toClass = SearchViewModel::class,
    toModule = ModuleNames.ViewModels
)
class SearchViewModel : ViewModel() {

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }
}