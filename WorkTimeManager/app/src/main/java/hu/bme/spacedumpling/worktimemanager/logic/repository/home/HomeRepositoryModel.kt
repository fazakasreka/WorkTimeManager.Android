package hu.bme.spacedumpling.worktimemanager.logic.repository.home

import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject

data class HomeRepositoryModel (
    val tasksByProjects : List<TasksByProject>
    )