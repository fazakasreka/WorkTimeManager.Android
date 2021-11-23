package hu.bme.spacedumpling.worktimemanager.logic.repository.appsettings

import hu.uni.corvinus.my.app.data.datasources.base.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class AppSettingsRepositoryImpl(
    override val coroutineContext: CoroutineContext = Dispatchers.IO,
    private val localDataSource: DataSource<AppSettingsModel>
): AppSettingsRepository, CoroutineScope {
    override val isLoggedIn: Flow<Boolean> = localDataSource.output.map { it.credentials != null }

    override fun getCredentials(): String? {
        return localDataSource.getData()?.credentials
    }

    override fun saveCredentials(credentials: String) {
        localDataSource.saveData(getData().copy(credentials = credentials))
    }

    private fun getData(): AppSettingsModel{
        return localDataSource.getData()?: AppSettingsModel()
    }

}