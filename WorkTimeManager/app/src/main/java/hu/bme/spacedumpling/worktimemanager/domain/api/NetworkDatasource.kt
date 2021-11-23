package hu.bme.spacedumpling.worktimemanager.domain.api

import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.logic.models.SimpleStatistic
import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeIntervalInput
import hu.bme.spacedumpling.worktimemanager.logic.repository.home.HomePageModel

interface NetworkDatasource{
    suspend fun fetchListOfProjects(): List<Project>
    suspend fun fetchProject(id: Int): Project
    suspend fun updateProject(project: Project)
    suspend fun fetchTasksByProjects(): List<TasksByProject>
    suspend fun fetchStatistics(): List<SimpleStatistic>
    suspend fun fetchHomePage(): HomePageModel
    suspend fun deleteTimeInterval(timeIntervalId: Int)
    suspend fun login(username: String, password: String): Boolean
    suspend fun saveTimeInterval(timeIntervalInput: TimeIntervalInput)
}