package hu.bme.spacedumpling.worktimemanager.presentation.page.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.logic.repository.projects.ProjectsRepository
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.FragmentAction
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.viewmodels.BaseViewModel
import hu.bme.spacedumpling.worktimemanager.presentation.cell.ProjectClickedAction
import hu.bme.spacedumpling.worktimemanager.presentation.cell.TestCellClicked
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

class ProjectDetailsViewModel(
    projectId: Int,
    private val projectsRepository: ProjectsRepository
) : BaseViewModel() {

    init{
        viewModelScope.launch {
            UIActionFlow.collect{
                when (it) {
                    is PageReloadRequest -> {
                        projectsRepository.fetchProjectDetails(projectId)
                    }
                    is UpdateProject -> {
                        projectsRepository.updateProject(it.project)
                    }
                }
            }
        }
    }

    val project : LiveData<Project> = liveData{
        projectsRepository.fetchProjectDetails(projectId)
        emitSource(
            projectsRepository.projectList.mapNotNull { projects ->
                projects.firstOrNull {
                    it.id == projectId
                }
            }.asLiveData()
        )
    }

}