package hu.bme.spacedumpling.worktimemanager.domain.api.dto

import hu.bme.spacedumpling.worktimemanager.logic.models.SimpleStatistic
import hu.bme.spacedumpling.worktimemanager.logic.models.StatisticType


data class UserStatisticsDto (
    val hoursForWeek: String?,
    val hoursForMonth: String?,
    val hoursAllTime: String?,
    val hoursPerProjects: List<ProjectHoursDto>?
        )

fun UserStatisticsDto.mapToModelSimpleStatisticsList() : List<SimpleStatistic>{
    val list = mutableListOf<SimpleStatistic>()
    //TODO extract these string resource somehow
    this.hoursForWeek?.let{
        list.add(SimpleStatistic("This week", this.hoursForWeek, StatisticType.PERSONAL_STATISTIC))
    }
    this.hoursForMonth?.let{
        list.add(SimpleStatistic("This month", this.hoursForMonth, StatisticType.PERSONAL_STATISTIC))
    }
    this.hoursAllTime?.let{
        list.add(SimpleStatistic("All time", this.hoursAllTime, StatisticType.PERSONAL_STATISTIC))
    }
    this.hoursPerProjects?.forEach { projectHours ->
        projectHours.toModelSimpleStatistics()?.let { list.add(it) }
    }
    return list
}