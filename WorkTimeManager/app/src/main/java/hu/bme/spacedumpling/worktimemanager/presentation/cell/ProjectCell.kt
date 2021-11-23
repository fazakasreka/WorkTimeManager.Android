package hu.bme.spacedumpling.worktimemanager.presentation.cell


import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bme.spacedumpling.worktimemanager.databinding.CellProjectBinding
import hu.bme.spacedumpling.worktimemanager.logic.models.Project
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction
import hu.bme.spacedumpling.worktimemanager.presentation.view.UnclickableTagView
import hu.bme.spacedumpling.worktimemanager.presentation.view.UserTagView
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
                        binding.sumTasks.tagUnclickable.text = " " + item.model.sumHours?: "0:00"
                        binding.projectManagerTags.removeAllViews()
                        item.model.leaders?.forEach{ leader ->
                            binding.projectManagerTags.addView(
                                UserTagView(context).apply { setUp(leader)}
                            )
                        }
                        binding.root.setOnClickListener {
                            callback.tryEmit(ProjectClickedAction(item.model.id))
                        }
                    }
                }
            )
    }
}

class ProjectClickedAction(val projectId: Int): UIAction
