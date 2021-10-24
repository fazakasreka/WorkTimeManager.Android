package hu.bme.spacedumpling.worktimemanager.presentation.cell.cells

import androidx.core.content.ContextCompat
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import hu.bitraptors.recyclerview.genericlist.GenericListInteractionListener
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.databinding.CellTestBinding
import hu.bme.spacedumpling.worktimemanager.presentation.cell.presentationmodels.TestItem

fun testDelegate(callback: GenericListInteractionListener) =
    adapterDelegateViewBinding<TestItem, GenericListItem, CellTestBinding>(
        viewBinding = { layoutInflater, root ->
            CellTestBinding.inflate(layoutInflater, root, false)
        },
        block = {
            bind {
                binding.testIcon.setImageDrawable(ContextCompat.getDrawable(context, item.icon))
                binding.testText.text = item.title
            }
        }
    )