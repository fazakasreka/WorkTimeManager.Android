package hu.bme.spacedumpling.worktimemanager.presentation.ui.page.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.logic.models.TestItem
import hu.bme.spacedumpling.worktimemanager.logic.repository.ProjectsRepository
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.FragmentAction
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.viewmodels.BaseViewModel
import hu.bme.spacedumpling.worktimemanager.presentation.ui.cell.ProjectCell
import hu.bme.spacedumpling.worktimemanager.presentation.ui.cell.TestCell
import hu.bme.spacedumpling.worktimemanager.presentation.ui.cell.TestCellClicked
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProjectViewModel(
    val projectsRepository: ProjectsRepository
): BaseViewModel() {
    init {
        projectsRepository.fetchProjectList()
    }

    //Actions

    val UIActionFlow = MutableSharedFlow<UIAction>(1)

    private val fragmentActionFlow = MutableSharedFlow<FragmentAction>(1)
    val fragmentActionLiveData = fragmentActionFlow.asLiveData()
    init{
        viewModelScope.launch {
            UIActionFlow.collect{
                if(it is TestCellClicked){
                    fragmentActionFlow.emit(MakeToast("Item clicked"))
                }
            }
        }
    }

    //Data
    val _projects = projectsRepository.projectList.map {
        it.map { ProjectCell(it) }
    }
    val projects : LiveData<List<GenericListItem>> = _projects.asLiveData()

}