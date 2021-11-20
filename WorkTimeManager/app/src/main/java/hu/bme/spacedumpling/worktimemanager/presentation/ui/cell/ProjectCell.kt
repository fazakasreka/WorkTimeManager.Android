package hu.bme.spacedumpling.worktimemanager.presentation.ui.cell


import androidx.core.content.ContextCompat
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.databinding.CellProjectBinding
import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction
import kotlinx.coroutines.flow.MutableSharedFlow

class ProjectCell(
    val model: Project
) : GenericListItem{
    override fun getItemHash(): Int {
        return hashCode()
    }

    override fun getItemId(): String {
        return this::class.java.name+model.title
    }

    companion object {
        fun getDelegate(callback: MutableSharedFlow<UIAction>) =
            adapterDelegateViewBinding<ProjectCell, GenericListItem, CellProjectBinding>(
                viewBinding = { layoutInflater, root ->
                    CellProjectBinding.inflate(layoutInflater, root, false)
                },
                block = {
                    bind {
                        binding.title.text = item.model.title
                        binding.description.text = item.model.description
                    }
                }
            )
    }
}
