package hu.bme.spacedumpling.worktimemanager.presentation.page.projects

import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.FragmentAction
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction

//FragmentActions - request form VM to Fragment
data class MakeToast(val text: String): FragmentAction
data class NavigateToProjectDetails(val projectId: Int): FragmentAction


//UI Actions - request forom Fragment to VM
class PageReloadRequest(): UIAction
data class UpdateProject(val project: Project): UIAction