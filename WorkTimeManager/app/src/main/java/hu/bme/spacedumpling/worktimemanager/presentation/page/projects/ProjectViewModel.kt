package hu.bme.spacedumpling.worktimemanager.presentation.page.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.viewmodels.BaseViewModel
import hu.bme.spacedumpling.worktimemanager.presentation.cell.presentationmodels.TestItem
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

class ProjectViewModel(): BaseViewModel() {

    val projects : LiveData<List<GenericListItem>> = flowOf(
        listOf(
            TestItem("Hello", R.drawable.ic_home),
            TestItem("Hola!", R.drawable.ic_project),
            TestItem("Berni is love, Berni is life", R.drawable.ic_statistics)
        )
    ).asLiveData()
}