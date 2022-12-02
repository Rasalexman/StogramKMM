package ru.stogram.android.features.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.stogram.repository.IPostsRepository
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val postsRepository: IPostsRepository
) : ViewModel() {

    private var onCreateJob: Job? = null

    fun onCreateClicked() {
        onCreateJob?.cancel()
        onCreateJob = viewModelScope.launch {
            postsRepository.addUserPostAsFlow().collect {
                println("NEW POST CREATED = ${it.id}")
            }
        }
    }

    override fun onCleared() {
        onCreateJob = null
        super.onCleared()
    }
}