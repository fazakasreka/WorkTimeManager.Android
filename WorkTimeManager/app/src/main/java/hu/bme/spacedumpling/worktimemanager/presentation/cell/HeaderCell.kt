package hu.bme.spacedumpling.worktimemanager.presentation.cell

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.databinding.CellHeaderBinding

class HeaderCell (val text : String) : GenericListItem {
    override fun getItemHash(): Int {
        return hashCode()
    }

    override fun getItemId(): String {
        return this::class.java.name + text
    }

    companion object {
        fun getDelegate() =
            adapterDelegateViewBinding<HeaderCell, GenericListItem, CellHeaderBinding>(
                viewBinding = { layoutInflater, root ->
                    CellHeaderBinding.inflate(layoutInflater, root, false)
                },
                block = {
                    bind{
                        binding.text.text = item.text
                    }
                }
            )
    }
}