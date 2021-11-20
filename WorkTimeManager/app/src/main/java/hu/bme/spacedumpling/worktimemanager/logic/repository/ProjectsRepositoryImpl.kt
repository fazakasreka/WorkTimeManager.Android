package hu.bme.spacedumpling.worktimemanager.logic.repository

import hu.bme.spacedumpling.worktimemanager.domain.api.WorkTimeManagerNetworkDatasource
import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class ProjectsRepositoryImpl(
    override val coroutineContext: CoroutineContext = Dispatchers.IO,
    private val networkSource: WorkTimeManagerNetworkDatasource,
) : ProjectsRepository, CoroutineScope {
    override val projectList: MutableSharedFlow<List<Project>> = MutableSharedFlow(1)

    override fun fetchProjectList(){
        launch(coroutineContext){
            try{
                projectList.emit(networkSource.fetchProfiles())
            }catch (e: Exception){
                println("API error")
            }
        }
    }
}