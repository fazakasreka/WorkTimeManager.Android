package hu.bme.spacedumpling.worktimemanager.presentation.page.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeIntervalInput
import hu.bme.spacedumpling.worktimemanager.logic.repository.appsettings.AppSettingsRepository
import hu.bme.spacedumpling.worktimemanager.logic.repository.home.HomeRepository
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.viewmodels.BaseViewModel
import hu.bme.spacedumpling.worktimemanager.presentation.cell.TimeIntervalCell
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.MakeToast
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.PageReloadRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    val homeRepository: HomeRepository,
    val appSettingsRepository: AppSettingsRepository
) : BaseViewModel() {
    //Actions
    init{
        viewModelScope.launch {
            UIActionFlow.collect{
                when (it) {
                    is PageReloadRequest -> {
                        homeRepository.fetchTasksByProjects()
                        homeRepository.fetchHomePage()
                    }
                    is ChooseProject -> {
                        _chosenProject.tryEmit(it.tasksByProject)
                    }
                    is SaveTimeInterval -> {
                        when(it.timeIntervalInput.validtate()) {
                            TimeIntervalInput.ValidationResult.VALID -> {
                                fragmentActionFlow.tryEmit(MakeToast("Saving..."))
                                homeRepository.saveTimeInterval(it.timeIntervalInput)
                            }
                            TimeIntervalInput.ValidationResult.NO_TASK -> {
                                fragmentActionFlow.tryEmit(MakeToast("Choose task"))
                            }
                            else -> {
                                fragmentActionFlow.tryEmit(MakeToast("Else branch"))
                            }
                        }
                    }
                    is DeleteTimeInterval -> {
                        homeRepository.deleteTimeInterval(it.timeIntervalId)
                    }
                    is Login -> {
                        homeRepository.login(it.username, it.password)
                    }
                }
            }
        }
    }

    //data for timeinterval saving
    val tasksByProjects = homeRepository.tasksByProjects.asLiveData()
    val _chosenProject = MutableSharedFlow<TasksByProject>(1)
    val chosenProject = _chosenProject.asLiveData()

    //data
    val timeIntervals : LiveData<List<GenericListItem>> = homeRepository.timeIntervals.map{ intervals -> intervals.map { TimeIntervalCell(it)}}.asLiveData()
    val username = homeRepository.username.asLiveData()
    val isLoggedIn = appSettingsRepository.isLoggedIn.asLiveData()
}