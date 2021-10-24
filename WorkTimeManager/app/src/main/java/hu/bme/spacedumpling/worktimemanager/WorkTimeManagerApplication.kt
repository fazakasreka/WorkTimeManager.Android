package hu.bme.spacedumpling.worktimemanager

import android.app.Application
import hu.bme.spacedumpling.worktimemanager.modules.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WorkTimeManagerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@WorkTimeManagerApplication)
            modules(viewModelModules)
        }
    }
}