package hu.bme.spacedumpling.worktimemanager.modules

import hu.bme.spacedumpling.worktimemanager.presentation.page.projects.ProjectViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module{
    viewModel{
        ProjectViewModel()
    }
}