package hu.bme.spacedumpling.worktimemanager.presentation.page.home

import hu.bme.spacedumpling.worktimemanager.logic.models.TasksByProject
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeInterval
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction


data class ChooseProject(val tasksByProject: TasksByProject): UIAction
data class SaveTimeInterval(val timeInterval: TimeInterval): UIAction