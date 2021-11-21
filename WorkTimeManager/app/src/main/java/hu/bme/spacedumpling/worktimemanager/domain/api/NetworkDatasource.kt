package hu.bme.spacedumpling.worktimemanager.domain.api

import hu.bme.spacedumpling.worktimemanager.logic.models.Project

interface NetworkDatasource{
    suspend fun fetchListOfProjects(): List<Project>
    suspend fun fetchProject(id: Int): Project
    suspend fun updateProject(project: Project)
}