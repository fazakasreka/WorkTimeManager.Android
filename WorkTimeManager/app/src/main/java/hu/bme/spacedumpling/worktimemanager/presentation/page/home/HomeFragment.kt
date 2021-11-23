package hu.bme.spacedumpling.worktimemanager.presentation.page.home

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import hu.bitraptors.recyclerview.setupRecyclerView
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeIntervalInput
import hu.bme.spacedumpling.worktimemanager.presentation.cell.TimeIntervalCell
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.MakeToast
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.NavigateToProjectDetails
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.PageReloadRequest
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.ProjectsFragmentDirections
import hu.bme.spacedumpling.worktimemanager.util.gone
import hu.bme.spacedumpling.worktimemanager.util.visible
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment: Fragment(
    R.layout.fragment_dashboard
) {
    private val viewModel by viewModel<HomeViewModel>()
    private var timeInterval = TimeIntervalInput()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTexts()
        setUpPickers()
        setUpTimePickers()
        setUpList()
        setUpUserName()
        setUpLogin()
        reloadPage()
        subscribeFragmentActions()
        setUpSaveButton()
    }

    private fun setUpLogin() {
        login_button.setOnClickListener {
            if (username.text != null && password.text != null) {
                viewModel.UIActionFlow.tryEmit(
                    Login(
                        username = username.text.toString(),
                        password = password.text.toString()
                    )
                )
            }
        }
        lifecycleScope.launch {
            viewModel.isLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
                if (isLoggedIn) {
                    time_interval_card.visible()
                    time_intervals_list.visible()
                    login.gone()
                } else {
                    time_interval_card.gone()
                    time_intervals_list.gone()
                    login.visible()
                }
            }
        }
    }

    private fun reloadPage(){
        viewModel.UIActionFlow.tryEmit(PageReloadRequest())
    }

    private fun setUpUserName() {
        viewModel.username.observe(viewLifecycleOwner){
            it?.let{dashboard_hello.text = getString(R.string.dashboar_hello_name, it)}
        }
    }

    private fun setUpList(){
        this.setupRecyclerView(
            recyclerView = time_intervals_list,
            items = viewModel.timeIntervals,
            delegates = listOf(
                TimeIntervalCell.getDelegate(viewModel.UIActionFlow),
            ).toTypedArray(),
        )
    }

    private fun setUpTexts(){
        interval_save_button.text = getString(R.string.home_save)
        dashboard_project_picker.hint = getString(R.string.home_project)
        dashboard_task_picker.hint = getString(R.string.home_task)
        dashboard_task_picker.isEnabled = false
    }

    private fun setUpPickers(){
        lifecycleScope.launch {
            viewModel.tasksByProjects.observe(viewLifecycleOwner) {
                val items = it
                val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
                (project_list as? AutoCompleteTextView)?.setAdapter(adapter)
                (project_list as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->
                    dashboard_task_picker.isEnabled = false
                    viewModel.UIActionFlow.tryEmit(ChooseProject(it[position]))
                    (task_list as? AutoCompleteTextView)?.setText("")
                    (task_list as? AutoCompleteTextView)?.clearFocus()
                }
            }
            viewModel.chosenProject.observe(viewLifecycleOwner){
                if(it.tasks.isNullOrEmpty()) {
                    dashboard_task_picker.hint = "No tasks in project"
                }else{
                    it.tasks.let{ tasks ->
                        dashboard_task_picker.isEnabled = true
                        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, tasks)
                        (task_list as? AutoCompleteTextView)?.setAdapter(adapter)
                        (task_list as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->
                            timeInterval = timeInterval.copy(taskId = tasks[position].id)
                        }
                    }
                }
            }
        }
    }

    private fun setUpTimePickers(){
        date_picker_button.setOnClickListener {
                val picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                picker.addOnPositiveButtonClickListener {
                    picker.selection?.let{
                        val date =  Date(it)
                        date_picker_button.text =date.toString()
                        timeInterval = timeInterval.copy(date = date)
                    }
                }
                picker.show(requireFragmentManager(), null)
        }
        start_time_picker.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            picker.addOnPositiveButtonClickListener {
                start_time_picker.text="${picker.hour} : ${picker.minute}"
                val cal = Calendar.getInstance()
                cal[Calendar.YEAR] = 1999
                cal[Calendar.MONTH] = 0
                cal[Calendar.DAY_OF_MONTH]= 0
                cal[Calendar.MINUTE] = picker.minute
                cal[Calendar.HOUR_OF_DAY] = picker.hour
                cal[Calendar.MINUTE] = picker.minute
                timeInterval = timeInterval.copy(startTime = cal.time)
            }
            picker.show(requireFragmentManager(), null)
        }
        end_time_picker.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            picker.addOnPositiveButtonClickListener {
                end_time_picker.text="${picker.hour} : ${picker.minute}"
                val cal = Calendar.getInstance()
                cal[Calendar.YEAR] = 1999
                cal[Calendar.MONTH] = 0
                cal[Calendar.DAY_OF_MONTH]= 0
                cal[Calendar.HOUR_OF_DAY] = picker.hour
                cal[Calendar.MINUTE] = picker.minute
                timeInterval = timeInterval.copy(endTime = cal.time)
            }
            picker.show(requireFragmentManager(), null)
        }

    }

    private fun setUpSaveButton(){
        interval_save_button.setOnClickListener {
            viewModel.UIActionFlow.tryEmit(SaveTimeInterval(timeInterval))
        }
    }

    private fun subscribeFragmentActions(){
        lifecycleScope.launch {
            viewModel.fragmentActionLiveData.observe(viewLifecycleOwner){
                when(it){
                    is MakeToast -> Toast.makeText(context, it.text, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}