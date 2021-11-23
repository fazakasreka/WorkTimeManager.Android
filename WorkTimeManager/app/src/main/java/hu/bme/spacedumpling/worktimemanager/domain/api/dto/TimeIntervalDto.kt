package hu.bme.spacedumpling.worktimemanager.domain.api.dto

import hu.bme.spacedumpling.worktimemanager.logic.models.TimeIntervalInput
import java.util.*

data class TimeIntervalDto(
    val name: String? = null,
    val description: String? = null,
    val startDate: Date,
    val endDate: Date,
    val taskId: Int
)

fun TimeIntervalInput.toTimeIntervalDtoMapper(): TimeIntervalDto{
    return TimeIntervalDto(
        taskId = this.taskId!!,
        startDate = this.startTime!!,
        endDate = this.endTime!!
    )
}