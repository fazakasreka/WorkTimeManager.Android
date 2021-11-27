package hu.bme.spacedumpling.worktimemanager.logic.repository.appsettings

import hu.uni.corvinus.my.app.data.datasources.base.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlin.coroutines.CoroutineContext


//DO NOT PUT NETWORK DATASOURCE IN THIS, CIRCULAR DEPENDENCY
class AppSettingsRepositoryImpl(
    override val coroutineContext: CoroutineContext = Dispatchers.IO,
    private val localDataSource: DataSource<AppSettingsModel>
): AppSettingsRepository, CoroutineScope {
    override val isLoggedIn: Flow<Boolean> = localDataSource.output.map { it.credentials != null }
    override val networkErrorMessage = MutableSharedFlow<String>(1)

    override fun getCredentials(): String? {
        return localDataSource.getData()?.credentials
    }

    override fun saveCredentials(credentials: String) {
        localDataSource.saveData(getData().copy(credentials = credentials))
    }

    override fun isLoggedIn(): Boolean {
        return localDataSource.getData()?.credentials != null
    }

    private fun getData(): AppSettingsModel{
        return localDataSource.getData()?: AppSettingsModel()
    }

    override fun logout() {
        localDataSource.saveData(getData().copy(credentials = null))
    }

    override fun emitNetworkErrorMessage(message: String) {
        networkErrorMessage.tryEmit(message)
    }
}