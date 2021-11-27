package hu.bme.spacedumpling.worktimemanager.presentation.page.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.logic.login.LogoutHandler
import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeIntervalInput
import hu.bme.spacedumpling.worktimemanager.logic.repository.appsettings.AppSettingsRepository
import hu.bme.spacedumpling.worktimemanager.logic.repository.home.HomeRepository
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.viewmodels.BaseViewModel
import hu.bme.spacedumpling.worktimemanager.presentation.cell.HeaderCell
import hu.bme.spacedumpling.worktimemanager.presentation.cell.TimeIntervalCell
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.MakeToast
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.PageReloadRequest
import hu.bme.spacedumpling.worktimemanager.util.weeksAgo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(
    val homeRepository: HomeRepository,
    val logoutHandler: LogoutHandler,
    appSettingsRepository: AppSettingsRepository
) : BaseViewModel(appSettingsRepository) {
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
                            TimeIntervalInput.ValidationResult.START_TIME_AFTER_END_TIME -> {
                                fragmentActionFlow.tryEmit(MakeToast("The starting time should be after the ending time"))
                            }
                        }
                    }
                    is DeleteTimeInterval -> {
                        homeRepository.deleteTimeInterval(it.timeIntervalId)
                    }
                    is Login -> {
                        homeRepository.login(it.username, it.password)
                    }
                    is Logout -> {
                        logoutHandler.handleLogout()
                    }
                }
            }
        }
    }

    fun isLoggedIn(): Boolean{
        return appSettingsRepository.isLoggedIn()
    }

    //data for timeinterval saving
    val tasksByProjects = homeRepository.tasksByProjects.asLiveData()
    val _chosenProject = MutableSharedFlow<TasksByProject>(1)
    val chosenProject = _chosenProject.asLiveData()

    //data
    val timeIntervals : LiveData<List<GenericListItem>> = homeRepository.timeIntervals.map{ intervals ->
        val list = mutableListOf<GenericListItem>()
        val thisWeek = mutableListOf<GenericListItem>()
        val thisMonth = mutableListOf<GenericListItem>()
        val earlier = mutableListOf<GenericListItem>()
        intervals.sortedBy { it.startDate }.forEach {
            it.startDate?.let{ startDate ->
                val date = Calendar.getInstance()
                date.time = startDate
                when {
                    date.weeksAgo() < 1 -> thisWeek.add(TimeIntervalCell(it))
                    date.weeksAgo() < 5 -> thisMonth.add(TimeIntervalCell(it))
                    else -> earlier.add(TimeIntervalCell(it))
                }
            }
        }
        if(thisWeek.isNotEmpty()){
            list.add(HeaderCell("This week"))
            list.addAll(thisWeek)
        }
        if(thisMonth.isNotEmpty()){
            list.add(HeaderCell("This month"))
            list.addAll(thisMonth)
        }
        if(earlier.isNotEmpty()){
            list.add(HeaderCell("Earlier"))
            list.addAll(earlier)
        }
        list
    }.asLiveData()


    val username = homeRepository.username.asLiveData()
    val isLoggedIn = appSettingsRepository.isLoggedIn.asLiveData()
    val loginError = homeRepository.loginError.asLiveData()
}