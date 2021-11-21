package hu.bme.spacedumpling.worktimemanager.logic.repository.projects

import hu.bme.spacedumpling.worktimemanager.domain.api.NetworkDatasource
import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.uni.corvinus.my.app.data.datasources.base.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class ProjectsRepositoryImpl(
    override val coroutineContext: CoroutineContext = Dispatchers.IO,
    private val networkSource: NetworkDatasource,
    private val localDataSource: DataSource<List<Project>>
) : ProjectsRepository, CoroutineScope {
    override val projectList: Flow<List<Project>> = localDataSource.output


    override fun fetchProjectList(){
        launch(coroutineContext){
            try{
                localDataSource.saveData(networkSource.fetchListOfProjects())
            }catch (e: Exception){
                println("API error")
            }
        }
    }

    override fun fetchProjectDetails(id: Int) {
        launch(coroutineContext){
            try{
                val newProject = networkSource.fetchProject(id)
                localDataSource.saveData(updateSingleFeedItemInList(getData(), newProject))
            }catch (e: Exception){
                println("API error")
            }
        }
    }

    override fun updateProject(project: Project) {
        launch(coroutineContext) {
                networkSource.updateProject(project)
                networkSource.fetchProject(project.id)
        }
    }


    //helper
    private fun getData() : List<Project>{
        return localDataSource.getData() ?: emptyList<Project>()
    }

    private fun updateSingleFeedItemInList(
        listOfNews: List<Project>,
        project: Project,
    ): List<Project> {
        val newList = listOfNews.toMutableList()
        val indexOfItem = newList.indexOfFirst {
            it.id == project.id
        }
        if (indexOfItem != -1) {
                newList[indexOfItem] = project
        } else {
            newList.add(project)
        }
        return newList
    }
}