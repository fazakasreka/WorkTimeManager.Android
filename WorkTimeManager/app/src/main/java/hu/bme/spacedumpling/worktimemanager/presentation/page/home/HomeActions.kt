package hu.bme.spacedumpling.worktimemanager.presentation.page.home

import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeIntervalInput
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction


data class ChooseProject(val tasksByProject: TasksByProject): UIAction
data class SaveTimeInterval(val timeIntervalInput: TimeIntervalInput): UIAction
data class DeleteTimeInterval(val timeIntervalId: Int): UIAction
data class Login(val username: String, val password: String): UIAction
class Logout(): UIAction