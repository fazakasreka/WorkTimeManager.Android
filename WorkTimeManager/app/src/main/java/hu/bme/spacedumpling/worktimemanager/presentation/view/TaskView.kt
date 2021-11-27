package hu.bme.spacedumpling.worktimemanager.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import hu.bme.spacedumpling.worktimemanager.databinding.ViewTaskBinding
import hu.bme.spacedumpling.worktimemanager.logic.models.Task

class TaskView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    private val binding = ViewTaskBinding.inflate(LayoutInflater.from(context), this, true)

    var task: Task? = null

    fun setUp(
        _task: Task
    ) {
        task = _task
        binding.tagView.text = " " + _task.title
    }

    fun getTaskOnUI() : Task?{
        return task
    }
}