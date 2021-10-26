package hu.bme.spacedumpling.worktimemanager.presentation.ui.page.projects

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.bitraptors.recyclerview.genericlist.GenericListInteractionListener
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.recylerview.setupClickableRecyclerView
import hu.bme.spacedumpling.worktimemanager.presentation.ui.cell.cells.testDelegate
import hu.bme.spacedumpling.worktimemanager.presentation.ui.cell.presentationmodels.TestItem
import kotlinx.android.synthetic.main.fragment_projects.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProjectsFragment: Fragment(
    R.layout.fragment_projects
) {
    private val viewModel by viewModel<ProjectViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupClickableRecyclerView<TestItem>(
            recyclerView = projects_list,
            items = viewModel.projects,
            delegates = {
                arrayOf(
                    testDelegate(it)
                )
            },
            onItemClicked = {
                Toast.makeText(context, "CLICKED!!!!@@@", Toast.LENGTH_LONG).show()
            }
        )
    }
}