package hu.bme.spacedumpling.worktimemanager.logic.models

import java.util.*

data class TimeInterval (
    val taskId: Int? = null,
    val date: Date? = null,
    val startTime: Date? = null,
    val endTime: Date? = null,
){

}