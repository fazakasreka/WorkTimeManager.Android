package hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.FragmentAction
import hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.actions.UIAction
import kotlinx.coroutines.flow.MutableSharedFlow

open class BaseViewModel : ViewModel() {

    val UIActionFlow = MutableSharedFlow<UIAction>(1)

    protected val fragmentActionFlow = MutableSharedFlow<FragmentAction>(1)
    val fragmentActionLiveData = fragmentActionFlow.asLiveData()
}