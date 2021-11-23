package hu.bme.spacedumpling.worktimemanager.presentation.page.projects

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.logic.models.Task
import hu.bme.spacedumpling.worktimemanager.logic.models.User
import hu.bme.spacedumpling.worktimemanager.presentation.view.TaskView
import hu.bme.spacedumpling.worktimemanager.presentation.view.UserTagView
import hu.bme.spacedumpling.worktimemanager.util.*
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
                    project_task_list.addView(TaskView(ctx).apply {setUp(it)})
                }
                project_leaders_tags.removeAllViews()
                project.leaders?.forEach {
                    project_leaders_tags.addView(UserTagView(ctx).apply { setUp(it, R.color.project_leader) })
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
            viewModel.UIActionFlow.tryEmit(UpdateProject(getLayoutData()))
        }
        project_description_edit_button.setOnClickListener {
            project_description.isEnabled = true
            project_description.requestFocus()
            project_description.showKeyboard()
            project_description_edit_button.invisible()
            project_description_done_button.visible()
        }
        project_description_done_button.setOnClickListener {
            project_description.isEnabled = false
            project_description.hideKeyboard()
            project_description_edit_button.visible()
            project_description_done_button.invisible()
        }

        project_task_add_button.setOnClickListener {
            showTaskAdderDialog()
        }
        project_leaders_add_button.setOnClickListener {
            showUserPickerDialog()
        }
    }

    private fun makePageEditable(){
        project_edit_button.invisible()
        project_save_button.visible()
        project_description_edit_button.visible()
        project_task_add_button.visible()
        project_leaders_add_button.visible()
    }

    private fun makePageUnEditable(){
        project_edit_button.visible()
        project_save_button.invisible()
        project_description_edit_button.invisible()
        project_task_add_button.invisible()
        project_leaders_add_button.invisible()
    }

    private fun getLayoutData(): Project {
        return Project(
            id = args.projectId,
            description = project_description.text.toString(),
            tasks = getTasksData(),
            leaders = getLeadersData()

        )
    }

    private fun getLeadersData(): List<User>{
        return project_leaders_tags.children.mapNotNull {
            (it as? UserTagView)?.getUserOnUI()
        }.toList()
    }

    private fun getTasksData(): List<Task>{
        return project_task_list.children.mapNotNull {
            (it as? TaskView)?.getTaskOnUI()
        }.toList()
    }

    private fun showTaskAdderDialog(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context?.getString(R.string.projects_task_add))

        val input = EditText(context)
        input.gravity = Gravity.CENTER
        input.setPadding(30)
        input.setTextSize(20.0f)
        builder.setView(input)

        builder.setPositiveButton(
            context?.getString(R.string.projects_ok)
        ) { dialog, which ->
            val taskTitle = input.text.toString()
            if(taskTitle.isNotBlank())addTask(taskTitle)
            else Toast.makeText(context, getString(R.string.task_name_cant_be_blank), Toast.LENGTH_LONG).show()
        }

        builder.setNegativeButton(
            context?.getString(R.string.projects_cancel)
        ) { _, _ ->}
        builder.show()
    }

    private fun addTask(title: String){
        context?.let{ ctx ->
            project_task_list.addView(TaskView(ctx).apply { setUp(Task(title, -1, null)) })
        }
    }

    private fun showUserPickerDialog(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context?.getString(R.string.projects_leader_add))

        val project = viewModel.project.value as Project
        val addableUsers =  project.allUsers?.filter{ user ->
            getLeadersData().find{ leader ->
                leader.id == user.id
            } == null
        }

        val input = TextView(context)
        input.gravity = Gravity.CENTER
        input.setPadding(30)
        input.setTextSize(20.0f)
        input.setPadding(20, 70, 20, 20)
        input.text = if(addableUsers.isNullOrEmpty())  getString(R.string.project_all_useres_added)
        else getString(R.string.project_choose_user)
        input.setTextAppearance(R.style.TextAppearanceBody3)
        builder.setView(input)

        var pickedUser : User? = null
        addableUsers?.let { addableUsers ->
            input.setOnClickListener {
                input.showDropDownMenu(
                    addableUsers.map {
                        it.name
                    },
                    { input.text = addableUsers[it].name
                        pickedUser = addableUsers[it]
                        input.setTextAppearance(R.style.ChosenUser)
                    }
                )
            }
        }

        builder.setPositiveButton(
            context?.getString(R.string.projects_ok)
        ) { dialog, which ->
            pickedUser?.let{addUser(it)}
        }

        builder.setNegativeButton(
            context?.getString(R.string.projects_cancel)
        ) { _, _ ->}
        builder.show()
    }

    private fun addUser(user: User){
        context?.let{ ctx ->
            project_leaders_tags.addView(UserTagView(ctx).apply { setUp(user) })
        }
    }
}
