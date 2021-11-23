package hu.bme.spacedumpling.worktimemanager.logic.repository.statistics

import hu.bme.spacedumpling.worktimemanager.domain.api.NetworkDatasource
import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.logic.models.SimpleStatistic
import hu.uni.corvinus.my.app.data.datasources.base.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class StatisticsRepositoryImpl (
    override val coroutineContext: CoroutineContext = Dispatchers.IO,
    private val networkSource: NetworkDatasource,
    private val localDataSource: DataSource<List<SimpleStatistic>>
) : StatisticsRepository, CoroutineScope {
    override val statisticList: Flow<List<SimpleStatistic>> = localDataSource.output


    override fun fetchStatisticList(){
        launch(coroutineContext){
            try{
                localDataSource.saveData(networkSource.fetchStatistics())
            }catch (e: Exception){
                println("API error")
            }
        }
    }


    //helper
    private fun getData() : List<SimpleStatistic>{
        return localDataSource.getData() ?: emptyList<SimpleStatistic>()
    }
}