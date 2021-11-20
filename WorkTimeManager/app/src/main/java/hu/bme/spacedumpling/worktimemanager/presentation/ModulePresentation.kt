package hu.bme.spacedumpling.worktimemanager.presentation.modules

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.bme.spacedumpling.worktimemanager.android.baseUrl
import hu.bme.spacedumpling.worktimemanager.domain.api.WorkTimeManagerAPIService
import hu.bme.spacedumpling.worktimemanager.domain.api.WorkTimeManagerNetworkDatasource
import hu.bme.spacedumpling.worktimemanager.domain.api.WorkTimeManagerNetworkDatasourceImpl
import hu.bme.spacedumpling.worktimemanager.logic.repository.ProjectsRepository
import hu.bme.spacedumpling.worktimemanager.logic.repository.ProjectsRepositoryImpl
import hu.bme.spacedumpling.worktimemanager.presentation.ui.page.dashboard.DashboardViewModel
import hu.bme.spacedumpling.worktimemanager.presentation.ui.page.projects.ProjectViewModel
import hu.bme.spacedumpling.worktimemanager.presentation.ui.page.statistics.StatisticsViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val presentationModule = module{
    //network
    factory<Moshi> {
        Moshi.Builder().apply {
            add(KotlinJsonAdapterFactory())
        }
            .build()
    }
    factory { MoshiConverterFactory.create(get()) }

    factory {
        OkHttpClient.Builder().build()
    }
    single{
        Retrofit.Builder()
            .client(get()).apply {
                addConverterFactory(get<MoshiConverterFactory>())
            }
            .baseUrl(baseUrl)
            .build()
    }

    factory {
        val retrofit = get<Retrofit>()
        retrofit.create(WorkTimeManagerAPIService::class.java)
    }

    single<WorkTimeManagerNetworkDatasource> {
        WorkTimeManagerNetworkDatasourceImpl(api = get())
    }

    //repository
    single<ProjectsRepository>{
        ProjectsRepositoryImpl(networkSource = get())
    }

    //presentation
    viewModel{
        ProjectViewModel(
            projectsRepository = get()
        )
    }
    viewModel{
        DashboardViewModel()
    }
    viewModel{
        StatisticsViewModel()
    }
}