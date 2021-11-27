package hu.bme.spacedumpling.worktimemanager.presentation.cell

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.databinding.CellTestBinding
import hu.bme.spacedumpling.worktimemanager.logic.models.TestItem
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction
import kotlinx.coroutines.flow.MutableSharedFlow

data class TestCell  (
    val model: TestItem,
    @DrawableRes val icon: Int = R.drawable.ic_statistics
) : GenericListItem{
    override fun getItemHash(): Int {
        return hashCode()
    }

    override fun getItemId(): String {
        return this::class.java.name+model.title
    }

    companion object {
        fun getDelegate(callback: MutableSharedFlow<UIAction>) =
            adapterDelegateViewBinding<TestCell, GenericListItem, CellTestBinding>(
                viewBinding = { layoutInflater, root ->
                    CellTestBinding.inflate(layoutInflater, root, false)
                },
                block = {
                    bind {
                        binding.testIcon.setImageDrawable(ContextCompat.getDrawable(context, item.icon))
                        binding.testText.text = item.model.title
                        binding.root.setOnClickListener { callback.tryEmit(TestCellClicked(item.model)) }
                    }
                }
            )
    }
}
data class TestCellClicked(val testItem: TestItem) : UIAction

