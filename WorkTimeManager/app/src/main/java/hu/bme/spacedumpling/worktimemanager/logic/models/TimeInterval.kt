package hu.bme.spacedumpling.worktimemanager.logic.models

import java.util.*

data class TimeInterval (
    val id: Int,
    val hours: String?,
    val taskName: String?,
    val projectName: String?,
    val startDate: Date?,
    val endDate: Date?,
)