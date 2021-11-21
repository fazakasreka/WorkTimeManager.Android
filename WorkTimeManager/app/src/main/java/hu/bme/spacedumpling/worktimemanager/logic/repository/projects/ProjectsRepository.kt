package hu.bme.spacedumpling.worktimemanager.logic.repository.projects

import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import kotlinx.coroutines.flow.Flow

interface ProjectsRepository {
    val projectList: Flow<List<Project>>
    fun fetchProjectList()
    fun fetchProjectDetails(id: Int)
    fun updateProject(project: Project)
}