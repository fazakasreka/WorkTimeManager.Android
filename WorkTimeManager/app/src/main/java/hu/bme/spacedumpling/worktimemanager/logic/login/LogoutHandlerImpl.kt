package hu.bme.spacedumpling.worktimemanager.logic.login

import hu.bme.spacedumpling.worktimemanager.logic.repository.appsettings.AppSettingsRepository
import hu.bme.spacedumpling.worktimemanager.logic.repository.home.HomeRepository
import hu.bme.spacedumpling.worktimemanager.logic.repository.projects.ProjectsRepository
import hu.bme.spacedumpling.worktimemanager.logic.repository.statistics.StatisticsRepository

class LogoutHandlerImpl(
    private val appSettingsRepository: AppSettingsRepository,
    private val homeRepository: HomeRepository,
    private val projectsRepository: ProjectsRepository,
    private val statisticsRepository: StatisticsRepository
) : LogoutHandler {
    override fun handleLogout() {
        appSettingsRepository.logout()
        homeRepository.onLogout()
        projectsRepository.onLogout()
        statisticsRepository.onLogout()
    }
}