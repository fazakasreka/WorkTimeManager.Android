package hu.bme.spacedumpling.worktimemanager.presentation.page.home

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import hu.bitraptors.recyclerview.setupRecyclerView
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.android.clockFormat
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeIntervalInput
import hu.bme.spacedumpling.worktimemanager.presentation.cell.NothingCell
import hu.bme.spacedumpling.worktimemanager.presentation.cell.TimeIntervalCell
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.MakeToast
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.PageReloadRequest
import hu.bme.spacedumpling.worktimemanager.util.gone
import hu.bme.spacedumpling.worktimemanager.util.invisible
import hu.bme.spacedumpling.worktimemanager.util.toDate
import hu.bme.spacedumpling.worktimemanager.util.visible
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment: Fragment(
    R.layout.fragment_dashboard
) {
    private val viewModel by viewModel<HomeViewModel>()
    private var timeIntervalInput = TimeIntervalInput()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTexts()
        setUpPickers()
        setUpTimePickers()
        setUpList()
        setUpUserName()
        setUpLogin()
        subscribeFragmentActions()
        setUpSaveButton()
    }

    private fun setUpLogin() {
        handleIsLoggedIn(viewModel.isLoggedIn())
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
        logut_button.setOnClickListener {
            viewModel.UIActionFlow.tryEmit(Logout())
        }
        lifecycleScope.launch {
            viewModel.isLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
                handleIsLoggedIn(isLoggedIn)
            }
            viewModel.loginError.observe(viewLifecycleOwner){
                password_container.error = getString(R.string.login_incorrect)
                username_container.error = getString(R.string.login_incorrect)
            }
        }
    }

    private fun handleIsLoggedIn(isLoggedIn:  Boolean){
        if (isLoggedIn) {
            loggedInLayoutChange()
            reloadPage()
        } else {
            loggedOutLayoutChange()
            dashboard_hello.text = getString(R.string.login_hello_stranger)
        }
    }

    private fun setUpUserName() {
        viewModel.username.observe(viewLifecycleOwner){
            it?.let{dashboard_hello.text = getString(R.string.dashboar_hello_name, it)}
        }
    }

    private fun setUpSaveButton(){
        interval_save_button.setOnClickListener {
            viewModel.UIActionFlow.tryEmit(SaveTimeInterval(timeIntervalInput))
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


    private fun setUpPickers(){
        dashboard_task_picker.isEnabled = false
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
                            timeIntervalInput = timeIntervalInput.copy(taskId = tasks[position].id)
                        }
                    }
                }
            }
        }
    }

    private fun setUpTimePickers(){
        refreshSumTime()
        date_picker_button.text = timeIntervalInput.date.toDate()
        date_picker_button.setOnClickListener {
                val picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                picker.addOnPositiveButtonClickListener {
                    picker.selection?.let{
                        val date =  Date(it)
                        date_picker_button.text =date.toDate()
                        timeIntervalInput = timeIntervalInput.copy(date = date)
                    }
                }
                picker.show(requireFragmentManager(), null)
        }
        start_time_picker.text = timeFormat(timeIntervalInput.startTimeHour, timeIntervalInput.startTimeMin)
        start_time_picker.setOnClickListener {
            showTimePicker(getString(R.string.interval_atart_picker_text)) { hour, minute ->
                start_time_picker.text= timeFormat( hour, minute)
                timeIntervalInput = timeIntervalInput.copy(startTimeHour = hour, startTimeMin = minute)
                refreshSumTime()
            }
        }
        end_time_picker.text = timeFormat(timeIntervalInput.endTimeHour, timeIntervalInput.endTimeMin)
        end_time_picker.setOnClickListener {
            showTimePicker(getString(R.string.time_interval_end_time_pick)) { hour, minute ->
                end_time_picker.text= timeFormat( hour, minute)
                timeIntervalInput = timeIntervalInput.copy(endTimeHour = hour, endTimeMin = minute)
                refreshSumTime()
            }
        }

    }

    private fun showTimePicker(title: String, callback: ((Int, Int,) -> Unit)){
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText(title)
            .build()
        picker.addOnPositiveButtonClickListener {
            callback(picker.hour, picker.minute)
        }
        picker.show(requireFragmentManager(), null)
    }

    private fun refreshSumTime(){
        interval_sum_time.text = timeFormat(timeIntervalInput.hoursBetween(), timeIntervalInput.minutesBetween())
    }

    private fun timeFormat(hour: Int?, minute: Int?):String{
        return if(minute == null || hour == null) ""
        else String.format(clockFormat, hour, minute)
    }

    private fun setUpTexts(){
        interval_save_button.text = getString(R.string.home_save)
        dashboard_project_picker.hint = getString(R.string.home_project)
        dashboard_task_picker.hint = getString(R.string.home_task)
        login_button.text = getString(R.string.login_login_button)
        username_container.helperText = getString(R.string.login_username)
        password_container.helperText = getString(R.string.login_password)
        dashboard_pls_login.text = getString(R.string.login_pls_login)
        dashboard_hello.text = getString(R.string.login_hello_stranger)
    }

    private fun reloadPage(){
        viewModel.UIActionFlow.tryEmit(PageReloadRequest())
    }

    private fun subscribeFragmentActions(){
        lifecycleScope.launch {
            viewModel.fragmentActionLiveData.observe(viewLifecycleOwner){
                when(it){
                    is MakeToast -> Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loggedInLayoutChange(){
        login_button.gone()
        logut_button.visible()

        time_interval_card.visible()
        time_intervals_list.visible()

        dashboard_pls_login.gone()
        login.gone()

    }

    private fun loggedOutLayoutChange(){
        login_button.visible()
        logut_button.invisible()

        time_interval_card.gone()
        time_intervals_list.gone()

        login.visible()
        dashboard_pls_login.visible()
    }
}