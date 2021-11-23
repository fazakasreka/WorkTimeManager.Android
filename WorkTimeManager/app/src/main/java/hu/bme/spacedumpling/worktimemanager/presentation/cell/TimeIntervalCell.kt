package hu.bme.spacedumpling.worktimemanager.presentation.cell

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.databinding.CellTestBinding
import hu.bme.spacedumpling.worktimemanager.databinding.CellTimeIntervalBinding
import hu.bme.spacedumpling.worktimemanager.logic.models.TestItem
import hu.bme.spacedumpling.worktimemanager.logic.models.TimeInterval
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction
import hu.bme.spacedumpling.worktimemanager.presentation.page.home.DeleteTimeInterval
import hu.bme.spacedumpling.worktimemanager.util.toDate
import hu.bme.spacedumpling.worktimemanager.util.toHourMin
import kotlinx.coroutines.flow.MutableSharedFlow

data class TimeIntervalCell (
    val model: TimeInterval
) : GenericListItem {
    //parse dates once no UI dateparsing
    val date = model.startDate?.toDate()
    val startTime = model.startDate?.toHourMin()
    val endTime = model.endDate?.toHourMin()

    override fun getItemHash(): Int {
        return hashCode()
    }

    override fun getItemId(): String {
        return this::class.java.name+model.id
    }

    companion object {
        fun getDelegate(callback: MutableSharedFlow<UIAction>) =
            adapterDelegateViewBinding<TimeIntervalCell, GenericListItem, CellTimeIntervalBinding>(
                viewBinding = { layoutInflater, root ->
                    CellTimeIntervalBinding.inflate(layoutInflater, root, false)
                },
                block = {
                    bind {
                        binding.dateButton.text = item.date
                        binding.startTimeButton.text = item.startTime
                        binding.endTimeButton.text = item.endTime
                        binding.project.text = context.getString(R.string.interval_cell_header, item.model.projectName, item.model.taskName)
                        binding.intervalSumTime.text = item.model.hours
                        binding.chonkerKuka.setOnClickListener {
                            callback.tryEmit(DeleteTimeInterval(item.model.id))
                        }
                    }
                }
            )
    }
}