package hu.bme.spacedumpling.worktimemanager.presentation.page.home

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeInterval
import hu.bme.spacedumpling.worktimemanager.logic.repository.home.HomeRepository
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.viewmodels.BaseViewModel
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.MakeToast
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.PageReloadRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    val homeRepository: HomeRepository
) : BaseViewModel() {
    //Actions
    init{
        viewModelScope.launch {
            UIActionFlow.collect{
                when (it) {
                    is PageReloadRequest -> {
                        homeRepository.fetchTasksByProjects()
                    }
                    is ChooseProject -> {
                        _chosenProject.tryEmit(it.tasksByProject)
                    }
                    is SaveTimeInterval -> {
                        when(it.timeInterval.validtate()) {
                            TimeInterval.ValidationResult.VALID -> {
                                fragmentActionFlow.tryEmit(MakeToast("Saving..."))
                            }
                            TimeInterval.ValidationResult.NO_TASK -> {
                                fragmentActionFlow.tryEmit(MakeToast("Choose task"))
                            }
                        }
                    }
                }
            }
        }
    }

    val tasksByProjects = homeRepository.tasksByProjects.asLiveData()
    val _chosenProject = MutableSharedFlow<TasksByProject>(1)
    val chosenProject = _chosenProject.asLiveData()
}