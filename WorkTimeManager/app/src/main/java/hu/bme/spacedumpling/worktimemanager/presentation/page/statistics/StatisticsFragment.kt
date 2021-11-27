package hu.bme.spacedumpling.worktimemanager.presentation.page.statistics

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import hu.bitraptors.recyclerview.setupRecyclerView
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.fragments.BaseFragment
import hu.bme.spacedumpling.worktimemanager.presentation.cell.NothingCell
import hu.bme.spacedumpling.worktimemanager.presentation.cell.SimpleStatisticCell
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.MakeToast
import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.PageReloadRequest
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.coroutines.launch

import org.koin.android.viewmodel.ext.android.viewModel

class StatisticsFragment: BaseFragment(
    R.layout.fragment_statistics
) {

    private val viewModel by viewModel<StatisticsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpText()
        setUpList()
        reloadPage()
        subscribeFragmentActions()
    }
    private fun setUpText(){
        header_title.text = getString(R.string.statistics_header_title)
    }

    private fun reloadPage(){
        viewModel.UIActionFlow.tryEmit(PageReloadRequest())
    }

    private fun setUpList(){
        this.setupRecyclerView(
            recyclerView = statistic_list,
            items = viewModel.statistics,
            delegates = listOf(
                NothingCell.getDelegate(),
                SimpleStatisticCell.getDelegate(viewModel.UIActionFlow),
            ).toTypedArray(),
        )
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
}