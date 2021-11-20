package hu.bme.spacedumpling.worktimemanager.logic.repository

import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import kotlinx.coroutines.flow.Flow

interface ProjectsRepository {
    val projectList: Flow<List<Project>>
    fun fetchProjectList()
}