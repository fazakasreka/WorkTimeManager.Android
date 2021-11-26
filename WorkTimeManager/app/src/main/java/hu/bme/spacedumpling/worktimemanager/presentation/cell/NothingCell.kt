package hu.bme.spacedumpling.worktimemanager.presentation.cell

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.databinding.CellNothingBinding
import hu.bme.spacedumpling.worktimemanager.util.gone
import hu.bme.spacedumpling.worktimemanager.util.invisible
import hu.bme.spacedumpling.worktimemanager.util.visible

class NothingCell (val showLoginTip : Boolean) : GenericListItem {
    override fun getItemHash(): Int {
        return hashCode()
    }

    override fun getItemId(): String {
        return this::class.java.name
    }

    companion object {
        fun getDelegate() =
            adapterDelegateViewBinding<NothingCell, GenericListItem, CellNothingBinding>(
                viewBinding = { layoutInflater, root ->
                    CellNothingBinding.inflate(layoutInflater, root, false)
                },
                block = {
                    bind{
                        if(item.showLoginTip) binding.emptyTip.visible()
                        else binding.emptyTip.invisible()
                    }
                }
            )
    }
}
