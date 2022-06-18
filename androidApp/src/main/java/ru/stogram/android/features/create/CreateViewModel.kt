package ru.stogram.android.features.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.kodi.core.immutableInstance
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.stogram.android.di.ModuleNames
import ru.stogram.repository.IPostsRepository

@BindSingle(
    toClass = CreateViewModel::class,
    toModule = ModuleNames.ViewModels
)
class CreateViewModel : ViewModel() {

    private var onCreateJob: Job? = null
    private val postsRepository: IPostsRepository by immutableInstance()

    fun onCreateClicked() {
        onCreateJob?.cancel()
        onCreateJob = viewModelScope.launch {
            postsRepository.addUserPostAsFlow().collect {
                println("NEW POST CREATED = ${it.id}")
            }
        }

    }
}