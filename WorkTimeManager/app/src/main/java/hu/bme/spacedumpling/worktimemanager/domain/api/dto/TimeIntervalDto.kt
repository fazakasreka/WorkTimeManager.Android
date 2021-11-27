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

fun TimeIntervalInput.toTimeIntervalDtoMapper(): TimeIntervalDto? {
    val startDate = this.getStartDate()
    val endDate = this.getEndDate()

    return if (startDate.after(endDate) || taskId == null) {
        null
    } else {
        TimeIntervalDto(
            taskId = this.taskId,
            startDate = startDate,
            endDate = endDate
        )
    }
}