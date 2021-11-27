package hu.bme.spacedumpling.worktimemanager.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.databinding.ViewUnclickableTagBinding

class UnclickableTagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    private val binding = ViewUnclickableTagBinding.inflate(LayoutInflater.from(context), this, true)

    fun setUp(
        text: String,
        color: Int = R.color.secondary_text
    ) {
        binding.tagView.text = text
        binding.tagView.setTextColor(context.getColor(color))
    }
}