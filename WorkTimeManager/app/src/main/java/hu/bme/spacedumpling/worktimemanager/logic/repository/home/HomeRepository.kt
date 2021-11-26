package hu.bme.spacedumpling.worktimemanager.logic.repository.home

import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeInterval
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeIntervalInput
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    val tasksByProjects : Flow<List<TasksByProject>>
    val username: Flow<String?>
    val timeIntervals: Flow<List<TimeInterval>>
    val loginError: Flow<Boolean>
    fun fetchTasksByProjects()
    fun fetchHomePage()
    fun deleteTimeInterval(timeIntervalId: Int)
    fun login (username: String, password: String)
    fun saveTimeInterval(timeIntervalInput: TimeIntervalInput)
    fun onLogout()
}