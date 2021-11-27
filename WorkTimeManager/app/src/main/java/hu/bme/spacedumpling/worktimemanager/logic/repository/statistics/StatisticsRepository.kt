package hu.bme.spacedumpling.worktimemanager.logic.repository.statistics

import hu.bme.spacedumpling.worktimemanager.logic.models.SimpleStatistic
import kotlinx.coroutines.flow.Flow

interface StatisticsRepository {
    val statisticList: Flow<List<SimpleStatistic>>
    fun fetchStatisticList()
    fun onLogout()
}