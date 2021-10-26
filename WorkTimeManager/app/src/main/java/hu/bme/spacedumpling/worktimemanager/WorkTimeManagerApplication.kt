package hu.bme.spacedumpling.worktimemanager

import android.app.Application
import hu.bme.spacedumpling.worktimemanager.presentation.modules.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WorkTimeManagerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(org.koin.core.logger.Level.ERROR)
            androidContext(this@WorkTimeManagerApplication)
            modules(viewModelModules)
        }
    }
}