package hu.bme.spacedumpling.worktimemanager.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.databinding.ViewUnclickableTagBinding
import hu.bme.spacedumpling.worktimemanager.logic.models.User

class UserTagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    private val binding = ViewUnclickableTagBinding.inflate(LayoutInflater.from(context), this, true)

    var user: User? = null

    fun setUp(
        _user: User,
        color: Int = R.color.project_leader
    ) {
        binding.tagView.text = _user.name
        user = _user
        binding.tagView.setTextColor(context.getColor(color))
    }

    fun getUserOnUI(): User?{
        return user
    }
}