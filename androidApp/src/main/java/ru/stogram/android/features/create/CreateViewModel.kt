package ru.stogram.android.features.create

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import ru.stogram.android.features.base.BaseViewModel
import ru.stogram.repository.IPostsRepository
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val postsRepository: IPostsRepository
) : BaseViewModel() {

    private var onCreateJob: Job? = null

    fun onCreateClicked() = launchOnMain {
        postsRepository.addUserPostAsFlow().collect {
            println("NEW POST CREATED = ${it.id}")
        }
    }

    override fun onCleared() {
        onCreateJob = null
        super.onCleared()
    }
}