package hu.bme.spacedumpling.worktimemanager.presentation.page.projects

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.presentation.view.TaskView
import hu.bme.spacedumpling.worktimemanager.presentation.view.UnclickableTagView
import hu.bme.spacedumpling.worktimemanager.util.invisible
import hu.bme.spacedumpling.worktimemanager.util.visible
import kotlinx.android.synthetic.main.fragment_project_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProjectDetailsFragment : Fragment(
    R.layout.fragment_project_details
) {

    private val args by navArgs<ProjectDetailsFragmentArgs>()
    private val viewModel by viewModel<ProjectDetailsViewModel>{
        parametersOf(args.projectId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reloadPage()
        setText()
        subscribeItemChange()
        subscribeOnClick()
    }

    private fun setText() {
        project_description_title.text = getString(R.string.project_description)
        project_leader_title.text = getString(R.string.project_leaders)
        project_task_title.text = getString(R.string.project_tasks)
    }

    private fun subscribeItemChange(){
        viewModel.project.observe(viewLifecycleOwner) { project ->
            context?.let { ctx ->
                project_title.text = project.title
                project_description.setText(project.description)
                project_task_list.removeAllViews()
                project.tasks?.forEach {
                    project_task_list.addView(TaskView(ctx).apply {setUp(it.title)})
                }
                project_leaders_tags.removeAllViews()
                project.leaders?.forEach {
                    project_leaders_tags.addView(UnclickableTagView(ctx).apply { setUp(it.name, R.color.project_leader) })
                }
            }
        }
    }

    private fun reloadPage() {
        viewModel.UIActionFlow.tryEmit(PageReloadRequest())
    }

    private fun subscribeOnClick(){
        project_edit_button.setOnClickListener {
            makePageEditable()
        }
        project_save_button.setOnClickListener {
            makePageUnEditable()
        }
    }

    private fun makePageEditable(){
        project_edit_button.invisible()
        project_save_button.visible()
        project_description_edit_button.visible()
        project_task_add_button.visible()
        project_leaders_add_button.visible()
        project_description.isEnabled = true
    }

    private fun makePageUnEditable(){
        project_edit_button.visible()
        project_save_button.invisible()
        project_description_edit_button.invisible()
        project_task_add_button.invisible()
        project_leaders_add_button.invisible()
        project_description.isEnabled = false
    }
}
