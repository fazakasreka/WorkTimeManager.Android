package hu.bme.spacedumpling.worktimemanager.domain.api

import hu.bme.spacedumpling.worktimemanager.domain.dto.toDtoProjectUpdateDto
import hu.bme.spacedumpling.worktimemanager.domain.dto.toModelProject
import hu.bme.spacedumpling.worktimemanager.logic.models.Project

class NetworkDatasourceImpl (
    val api : APIService
    ): NetworkDatasource {

    override suspend fun fetchListOfProjects(): List<Project>{
        return api.getListOfProjects().map{
            it.toModelProject()
        }
    }

    override suspend fun fetchProject(id: Int): Project {
        return api.getProjectDetails(id).toModelProject()
    }

    override suspend fun updateProject(project: Project) {
        return api.updateProject(project.toDtoProjectUpdateDto())
    }

}