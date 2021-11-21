package hu.bme.spacedumpling.worktimemanager.logic.repository.home

import hu.bme.spacedumpling.worktimemanager.domain.api.NetworkDatasource
import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
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

    //helper
    private fun getData() : HomeRepositoryModel{
        return localDataSource.getData() ?: HomeRepositoryModel(tasksByProjects = emptyList())
    }
}