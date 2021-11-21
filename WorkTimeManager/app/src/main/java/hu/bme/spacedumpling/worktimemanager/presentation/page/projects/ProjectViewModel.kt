package hu.bme.spacedumpling.worktimemanager.presentation.page.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.logic.repository.projects.ProjectsRepository
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.FragmentAction
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.viewmodels.BaseViewModel
import hu.bme.spacedumpling.worktimemanager.presentation.cell.ProjectCell
import hu.bme.spacedumpling.worktimemanager.presentation.cell.ProjectClickedAction
import hu.bme.spacedumpling.worktimemanager.presentation.cell.TestCellClicked
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProjectViewModel(
    val projectsRepository: ProjectsRepository
): BaseViewModel() {
    //Actions
    init{
        viewModelScope.launch {
            UIActionFlow.collect{
                when (it) {
                    is PageReloadRequest -> {
                        projectsRepository.fetchProjectList()
                    }
                    is ProjectClickedAction -> {
                        fragmentActionFlow.emit(NavigateToProjectDetails(it.projectId))
                    }
                }
            }
        }
    }

    //Data
    val projects : LiveData<List<GenericListItem>> = projectsRepository.projectList.map { projects ->
        projects.map { ProjectCell(it) }
    }.asLiveData()

}