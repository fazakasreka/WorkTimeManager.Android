package hu.bme.spacedumpling.worktimemanager.domain.api.dto

import hu.bme.spacedumpling.worktimemanager.logic.repository.home.HomePageModel

data class HomePageDto (
    val username: String,
    val userTimeIntervalItems: List<UserTimeIntervalItemDto>?
        )

fun HomePageDto.toHomePageModel(): HomePageModel{
    return HomePageModel(
        username = this.username, timeIntervals = this.userTimeIntervalItems?.map { it.mapToModelTimeInterval() } ?: emptyList()
    )
}

