package hu.bme.spacedumpling.worktimemanager.logic.repository.home

import hu.bme.spacedumpling.worktimemanager.logic.models.TimeInterval

data class HomePageModel (
    val username: String,
    val timeIntervals: List<TimeInterval>
        )