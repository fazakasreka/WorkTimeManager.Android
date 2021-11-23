package hu.bme.spacedumpling.worktimemanager.domain.api.dto

import hu.bme.spacedumpling.worktimemanager.logic.models.TimeInterval
import java.util.*

data class UserTimeIntervalItemDto (
    val id: Int,
    val hours: String?,
    val name: String?,
    val taskName: String?,
    val projectName: String?,
    val description: String?,
    val startDate: Date,
    val endDate: Date,
    )

fun UserTimeIntervalItemDto.mapToModelTimeInterval(): TimeInterval{
    return TimeInterval(
        id = this.id,
        hours = this.hours,
        taskName = this.taskName,
        projectName = this.projectName,
        startDate = this.startDate,
        endDate = this.endDate
    )
}