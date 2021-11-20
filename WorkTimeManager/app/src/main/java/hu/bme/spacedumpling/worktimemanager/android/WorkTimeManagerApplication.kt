package hu.bme.spacedumpling.worktimemanager.android

import android.app.Application
import hu.bme.spacedumpling.worktimemanager.domain.networkModule
import hu.bme.spacedumpling.worktimemanager.logic.repositoryModule
import hu.bme.spacedumpling.worktimemanager.presentation.modules.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WorkTimeManagerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(org.koin.core.logger.Level.ERROR)
            androidContext(this@WorkTimeManagerApplication)
            modules(presentationModule, networkModule, repositoryModule)
        }
    }
}