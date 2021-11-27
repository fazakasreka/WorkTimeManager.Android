package hu.bme.spacedumpling.worktimemanager.domain.api.dto

import hu.bme.spacedumpling.worktimemanager.logic.models.SimpleStatistic
import hu.bme.spacedumpling.worktimemanager.logic.models.StatisticType

data class ProjectHoursDto (
    val name: String?,
    val hours: String?,
        )

fun ProjectHoursDto.toModelSimpleStatistics(): SimpleStatistic? {
    return if (this.hours != null && this.name != null) {
        SimpleStatistic(this.name, this.hours, StatisticType.PROJECT_STATISTICS)
    } else null
}