package hu.bme.spacedumpling.worktimemanager.presentation.ui.page.projects

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import hu.bitraptors.recyclerview.setupRecyclerView
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.presentation.ui.cell.ProjectCell
import hu.bme.spacedumpling.worktimemanager.presentation.ui.cell.TestCell
import kotlinx.android.synthetic.main.fragment_projects.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.coroutineContext

class ProjectsFragment: Fragment(
    R.layout.fragment_projects
) {
    private val viewModel by viewModel<ProjectViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupRecyclerView(
            recyclerView = projects_list,
            items = viewModel.projects,
            delegates = listOf(
                TestCell.getDelegate(viewModel.UIActionFlow),
                ProjectCell.getDelegate(viewModel.UIActionFlow)
            ).toTypedArray(),
        )
        lifecycleScope.launch {
            viewModel.fragmentActionLiveData.observe(viewLifecycleOwner){
                when(it){
                    is MakeToast -> Toast.makeText(context, it.text, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}