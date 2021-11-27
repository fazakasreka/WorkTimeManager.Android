package hu.bme.spacedumpling.worktimemanager.logic.models

import androidx.annotation.ColorRes
import hu.bme.spacedumpling.worktimemanager.R

data class SimpleStatistic (
    val name: String,
    val hours: String,
    val statisticType:StatisticType
    )

enum class StatisticType(@ColorRes val color: Int){
    PROJECT_STATISTICS(R.color.statistics_project_text),
    PERSONAL_STATISTIC(R.color.statistics_text)
}