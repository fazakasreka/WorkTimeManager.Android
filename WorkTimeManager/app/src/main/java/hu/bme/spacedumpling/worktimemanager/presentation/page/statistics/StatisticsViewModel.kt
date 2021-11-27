package hu.bme.spacedumpling.worktimemanager.presentation.page.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.logic.repository.appsettings.AppSettingsRepository
import hu.bme.spacedumpling.worktimemanager.logic.repository.statistics.StatisticsRepository
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.viewmodels.BaseViewModel
import hu.bme.spacedumpling.worktimemanager.presentation.cell.NothingCell
import hu.bme.spacedumpling.worktimemanager.presentation.cell.ProjectCell
import hu.bme.spacedumpling.worktimemanager.presentation.cell.SimpleStatisticCell
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.MakeToast
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.PageReloadRequest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val statisticsRepository: StatisticsRepository,
    appSettingsRepository: AppSettingsRepository
) : BaseViewModel(appSettingsRepository) {

    //Actions
    init{
        viewModelScope.launch {
            UIActionFlow.collect{
                when (it) {
                    is PageReloadRequest -> {
                        statisticsRepository.fetchStatisticList()
                    }
                }
            }
        }
    }

    //Data
    val statistics : LiveData<List<GenericListItem>> = statisticsRepository.statisticList.combine(appSettingsRepository.isLoggedIn){ statistics, isLoggedIn ->
        if (statistics.isEmpty()) {
            listOf<GenericListItem>(NothingCell(!isLoggedIn))
        }
        else {
            statistics.map { SimpleStatisticCell(it) }
        }
    }.asLiveData()
}