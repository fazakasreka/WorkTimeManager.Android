package hu.bme.spacedumpling.worktimemanager.presentation.page.home

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeInterval
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.PageReloadRequest
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment: Fragment(
    R.layout.fragment_dashboard
) {
    private val viewModel by viewModel<HomeViewModel>()
    private var timeInterval = TimeInterval()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTexts()
        setUpPickers()
        setUpTimePickers()
        reloadPage()
    }

    private fun reloadPage(){
        viewModel.UIActionFlow.tryEmit(PageReloadRequest())
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
        viewModel.UIActionFlow.tryEmit(SaveTimeInterval(timeInterval))
    }
}