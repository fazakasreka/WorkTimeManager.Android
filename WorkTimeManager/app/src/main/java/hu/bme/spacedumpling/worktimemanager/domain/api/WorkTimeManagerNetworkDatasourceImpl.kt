package hu.bme.spacedumpling.worktimemanager.domain.api

import android.provider.ContactsContract
import hu.bme.spacedumpling.worktimemanager.domain.dto.toModelProject
import hu.bme.spacedumpling.worktimemanager.logic.models.Project

class WorkTimeManagerNetworkDatasourceImpl (
    val api : WorkTimeManagerAPIService
    ): WorkTimeManagerNetworkDatasource {

    override suspend fun fetchProfiles(): List<Project>{
        return api.getListOfProjects().map{
            it.toModelProject()
        }
    }

}