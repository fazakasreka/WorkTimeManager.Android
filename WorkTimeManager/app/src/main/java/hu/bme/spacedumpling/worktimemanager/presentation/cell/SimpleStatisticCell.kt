package hu.bme.spacedumpling.worktimemanager.presentation.cell

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.databinding.CellStatisticsBinding
import hu.bme.spacedumpling.worktimemanager.databinding.CellTestBinding
import hu.bme.spacedumpling.worktimemanager.logic.models.SimpleStatistic
import hu.bme.spacedumpling.worktimemanager.logic.models.StatisticType
import hu.bme.spacedumpling.worktimemanager.logic.models.TestItem
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction
import kotlinx.coroutines.flow.MutableSharedFlow

data class SimpleStatisticCell (
    val model: SimpleStatistic
) : GenericListItem {
    override fun getItemHash(): Int {
        return hashCode()
    }

    override fun getItemId(): String {
        return this::class.java.name+model.name + model.hours
    }

    companion object {
        fun getDelegate(callback: MutableSharedFlow<UIAction>) =
            adapterDelegateViewBinding<SimpleStatisticCell, GenericListItem, CellStatisticsBinding>(
                viewBinding = { layoutInflater, root ->
                    CellStatisticsBinding.inflate(layoutInflater, root, false)
                },
                block = {
                    bind {
                        binding.project.text = item.model.name
                        binding.intervalSumTime.text = item.model.hours
                        when(item.model.statisticType){
                            StatisticType.PROJECT_STATISTICS -> binding.project.setTextColor(context.getColor(R.color.statistics_project_text))
                            StatisticType.PERSONAL_STATISTIC -> binding.project.setTextColor(context.getColor(R.color.statistics_text))
                        }
                    }
                }
            )
    }
}