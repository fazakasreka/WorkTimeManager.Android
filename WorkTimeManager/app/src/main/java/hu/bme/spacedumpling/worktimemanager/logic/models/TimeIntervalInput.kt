package hu.bme.spacedumpling.worktimemanager.logic.models

import hu.bme.spacedumpling.worktimemanager.android.defaultEndHour
import hu.bme.spacedumpling.worktimemanager.android.defaultEndMinute
import hu.bme.spacedumpling.worktimemanager.android.defaultStartHour
import hu.bme.spacedumpling.worktimemanager.android.defaultStartMinute
import hu.bme.spacedumpling.worktimemanager.domain.api.dto.toTimeIntervalDtoMapper
import hu.bme.spacedumpling.worktimemanager.util.hoursBetween
import hu.bme.spacedumpling.worktimemanager.util.minutesBetweenModulo
import java.util.*

data class TimeIntervalInput (
    val taskId: Int? = null,
    val date: Date = Calendar.getInstance().time,
    val startTimeHour: Int = defaultStartHour,
    val startTimeMin: Int = defaultStartMinute,
    val endTimeHour: Int = defaultEndHour,
    val endTimeMin: Int = defaultEndMinute
){
    fun validtate() : ValidationResult{
       return when {
            taskId == null -> ValidationResult.NO_TASK
            startTimeHour > endTimeHour || startTimeHour == endTimeHour && startTimeMin >= endTimeMin -> ValidationResult.START_TIME_AFTER_END_TIME
           else -> ValidationResult.VALID
        }

    }

    fun getStartDate(): Date {
        val cal = Calendar.getInstance()
        cal.time = this.date
        cal[Calendar.HOUR] = this.startTimeHour
        cal[Calendar.MINUTE] = this.startTimeMin
        return cal.time
    }

    fun getEndDate(): Date {
        val cal = Calendar.getInstance()
        cal.time = this.date
        cal[Calendar.HOUR] = this.endTimeHour
        cal[Calendar.MINUTE] = this.endTimeMin
        return cal.time
    }

    fun hoursBetween() : Int?{
        return getStartDate().hoursBetween(getEndDate())
    }

    fun minutesBetween() : Int?{
        return getStartDate().minutesBetweenModulo(getEndDate())
    }


    enum class ValidationResult{
        VALID,
        NO_TASK,
        START_TIME_AFTER_END_TIME,
    }

}