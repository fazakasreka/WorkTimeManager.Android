package hu.bme.spacedumpling.worktimemanager.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import hu.bme.spacedumpling.worktimemanager.databinding.ViewTaskBinding

class TaskView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    private val binding = ViewTaskBinding.inflate(LayoutInflater.from(context), this, true)

    fun setUp(
        text: String
    ) {
        binding.tagView.text = " " + text
    }
}