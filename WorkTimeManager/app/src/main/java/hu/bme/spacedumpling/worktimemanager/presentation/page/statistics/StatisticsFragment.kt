package hu.bme.spacedumpling.worktimemanager.presentation.page.statistics

import android.os.Bundle
import android.view.View
import hu.bitraptors.recyclerview.setupRecyclerView
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.fragments.BaseFragment
import hu.bme.spacedumpling.worktimemanager.presentation.cell.SimpleStatisticCell
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.PageReloadRequest
import kotlinx.android.synthetic.main.fragment_statistics.*

import org.koin.android.viewmodel.ext.android.viewModel

class StatisticsFragment: BaseFragment(
    R.layout.fragment_statistics
) {

    private val viewModel by viewModel<StatisticsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
        reloadPage()
    }

    private fun reloadPage(){
        viewModel.UIActionFlow.tryEmit(PageReloadRequest())
    }

    private fun setUpList(){
        this.setupRecyclerView(
            recyclerView = statistic_list,
            items = viewModel.statistics,
            delegates = listOf(
                SimpleStatisticCell.getDelegate(viewModel.UIActionFlow),
            ).toTypedArray(),
        )
    }
}