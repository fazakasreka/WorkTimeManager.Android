package hu.bme.spacedumpling.worktimemanager.logic.models

import java.util.*

data class TimeInterval (
    val taskId: Int? = null,
    val date: Date? = null,
    val startTime: Date? = null,
    val endTime: Date? = null,
){
    fun validtate() : ValidationResult{
        if(taskId == null) return ValidationResult.NO_TASK
        if(startTime == null || endTime == null) return ValidationResult.NO_TIME
        if(date == null) return ValidationResult.NO_DATE
        if(startTime.after(endTime)){
            return ValidationResult.START_TIME_AFTER_END_TIME
        }
        return ValidationResult.VALID
    }

    enum class ValidationResult{
        VALID,
        NO_DATE,
        NO_TASK,
        NO_TIME,
        START_TIME_AFTER_END_TIME,
    }

}