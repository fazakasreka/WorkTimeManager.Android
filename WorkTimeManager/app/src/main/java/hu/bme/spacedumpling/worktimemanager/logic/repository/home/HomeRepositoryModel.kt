package hu.bme.spacedumpling.worktimemanager.logic.repository.home

import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeInterval

data class HomeRepositoryModel (
    val tasksByProjects : List<TasksByProject>,
    val username: String?,
    val timeIntervals: List<TimeInterval>
    )