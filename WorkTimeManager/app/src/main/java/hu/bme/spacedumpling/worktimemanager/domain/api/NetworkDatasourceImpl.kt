package hu.bme.spacedumpling.worktimemanager.domain.api

import android.util.Log
import hu.bme.spacedumpling.worktimemanager.domain.api.dto.*
import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.logic.models.SimpleStatistic
import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeIntervalInput
import hu.bme.spacedumpling.worktimemanager.logic.repository.home.HomePageModel

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

    override suspend fun fetchTasksByProjects(): List<TasksByProject> {
        return api.getTasksByProjects().map { it.mapToModelTasksByProject() }
    }

    override suspend fun fetchStatistics(): List<SimpleStatistic> {
        return api.getStatistics().mapToModelSimpleStatisticsList()
    }

    override suspend fun fetchHomePage(): HomePageModel {
        return api.getHomePage().toHomePageModel()
    }

    override suspend fun deleteTimeInterval(timeIntervalId: Int){
        return api.deleteTimeInterval(timeIntervalId)
    }

    override suspend fun login(username: String, password: String) : Boolean {
        return api.login(LoginDto(username= username, password = password))
    }

    override suspend fun saveTimeInterval(timeIntervalInput: TimeIntervalInput) {
        timeIntervalInput.toTimeIntervalDtoMapper()?.let{
            return api.saveTimeInterval(it)
        }
    }

}