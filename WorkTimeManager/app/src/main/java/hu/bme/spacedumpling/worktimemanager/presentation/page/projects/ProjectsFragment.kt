package hu.bme.spacedumpling.worktimemanager.presentation.page.projects

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import hu.bitraptors.recyclerview.setupRecyclerView
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.presentation.cell.ProjectCell
import hu.bme.spacedumpling.worktimemanager.presentation.cell.TestCell
import hu.bme.spacedumpling.worktimemanager.presentation.page.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.fragment_projects.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class ProjectsFragment: Fragment(
    R.layout.fragment_projects
) {
    private val viewModel by viewModel<ProjectViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
        subscribeFragmentActions()
        reloadPage()
        setText()
    }

    private fun setText(){
        header_title.text = context?.getString(R.string.projects_title) ?: ""
    }

    private fun reloadPage(){
        viewModel.UIActionFlow.tryEmit(PageReloadRequest())
    }

    private fun setUpList(){
        this.setupRecyclerView(
            recyclerView = projects_list,
            items = viewModel.projects,
            delegates = listOf(
                TestCell.getDelegate(viewModel.UIActionFlow),
                ProjectCell.getDelegate(viewModel.UIActionFlow)
            ).toTypedArray(),
        )
    }

    private fun subscribeFragmentActions(){
        lifecycleScope.launch {
            viewModel.fragmentActionLiveData.observe(viewLifecycleOwner){
                when(it){
                    is MakeToast -> Toast.makeText(context, it.text, Toast.LENGTH_LONG).show()
                    is NavigateToProjectDetails -> findNavController().navigate(ProjectsFragmentDirections.toProjectDetailsFragment(it.projectId))
                }
            }
        }
    }
}