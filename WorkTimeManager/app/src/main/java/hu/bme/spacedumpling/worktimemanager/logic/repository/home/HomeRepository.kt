package hu.bme.spacedumpling.worktimemanager.logic.repository.home

import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    val tasksByProjects : Flow<List<TasksByProject>>
    fun fetchTasksByProjects()
}