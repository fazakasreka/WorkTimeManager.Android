package hu.bme.spacedumpling.worktimemanager.presentation.modules

import hu.bme.spacedumpling.worktimemanager.presentation.ui.page.projects.ProjectViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module{
    viewModel{
        ProjectViewModel()
    }
}