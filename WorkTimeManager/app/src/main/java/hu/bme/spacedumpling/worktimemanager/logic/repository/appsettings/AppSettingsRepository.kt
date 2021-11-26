package hu.bme.spacedumpling.worktimemanager.logic.repository.appsettings

import kotlinx.coroutines.flow.Flow

//DO NOT PUT NETWORK DATASOURCE IN THIS, CIRCULAR DEPENDENCY
interface AppSettingsRepository {
    val isLoggedIn: Flow<Boolean>
    val networkErrorMessage: Flow<String>
    fun getCredentials(): String?
    fun saveCredentials(credentials: String)
    fun logout()
    fun emitNetworkErrorMessage(message: String)
    fun isLoggedIn(): Boolean
}