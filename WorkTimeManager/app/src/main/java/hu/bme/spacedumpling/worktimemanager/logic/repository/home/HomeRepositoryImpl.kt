package hu.bme.spacedumpling.worktimemanager.logic.repository.home

import hu.bme.spacedumpling.worktimemanager.domain.api.NetworkDatasource
import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeInterval
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeIntervalInput
import hu.uni.corvinus.my.app.data.datasources.base.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class HomeRepositoryImpl (
    override val coroutineContext: CoroutineContext = Dispatchers.IO,
    private val networkSource: NetworkDatasource,
    private val localDataSource: DataSource<HomeRepositoryModel>
): HomeRepository, CoroutineScope{
    override val tasksByProjects: Flow<List<TasksByProject>> = localDataSource.output.map {
        it.tasksByProjects
    }
    override val username: Flow<String?> = localDataSource.output.map { it.username }
    override val timeIntervals: Flow<List<TimeInterval>> = localDataSource.output.map { it.timeIntervals }

    override fun fetchTasksByProjects() {
        launch(coroutineContext){
            try{
                localDataSource.saveData(
                    getData().copy(
                        tasksByProjects = networkSource.fetchTasksByProjects()
                    )
                )
            }catch (e: Exception){
                println("API error")
            }
        }
    }

    override fun fetchHomePage() {
        launch(coroutineContext){
            try{
                val homePage = networkSource.fetchHomePage()
                localDataSource.saveData(
                    getData().copy(
                        username = homePage.username,
                        timeIntervals = homePage.timeIntervals
                    )
                )
            }catch (e: Exception){
                println("API error")
            }
        }
    }

    override fun deleteTimeInterval(timeIntervalId: Int) {
        launch(coroutineContext){
            try{
                networkSource.deleteTimeInterval(timeIntervalId)
                val homePage = networkSource.fetchHomePage()
                localDataSource.saveData(
                    getData().copy(
                        username = homePage.username,
                        timeIntervals = homePage.timeIntervals
                    )
                )
            }catch (e: Exception){
                println("API error")
            }
        }
    }

    override fun login(username: String, password: String) {
        launch(coroutineContext){
            try{
                networkSource.login(username, password)
            }catch (e: Exception){
                println("API error")
            }
        }
    }

    override fun saveTimeInterval(timeIntervalInput: TimeIntervalInput) {
        launch(coroutineContext){
            try{
                networkSource.saveTimeInterval(timeIntervalInput)
                val homePage = networkSource.fetchHomePage()
                localDataSource.saveData(
                    getData().copy(
                        username = homePage.username,
                        timeIntervals = homePage.timeIntervals
                    )
                )
            }catch (e: Exception){
                println("API error")
                throw e
            }
        }
    }

    //helper
    private fun getData() : HomeRepositoryModel{
        return localDataSource.getData()
            ?: HomeRepositoryModel(tasksByProjects = emptyList(), username = null, timeIntervals = emptyList())
    }
}