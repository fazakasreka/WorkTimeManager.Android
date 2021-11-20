package hu.bme.spacedumpling.worktimemanager.domain.api

import hu.bme.spacedumpling.worktimemanager.logic.models.Project

interface WorkTimeManagerNetworkDatasource{
    suspend fun fetchProfiles(): List<Project>
}