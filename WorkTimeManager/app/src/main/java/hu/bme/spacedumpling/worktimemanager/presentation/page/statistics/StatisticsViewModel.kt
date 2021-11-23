package hu.bme.spacedumpling.worktimemanager.presentation.page.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.logic.repository.statistics.StatisticsRepository
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.viewmodels.BaseViewModel
import hu.bme.spacedumpling.worktimemanager.presentation.cell.SimpleStatisticCell
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.PageReloadRequest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class StatisticsViewModel(
    val statisticsRepository: StatisticsRepository
) : BaseViewModel() {

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
    val statistics : LiveData<List<GenericListItem>> = statisticsRepository.statisticList.map{ list ->
        list.map { SimpleStatisticCell(it) }
    }.asLiveData()
}