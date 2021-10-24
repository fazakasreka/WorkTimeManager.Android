package hu.bme.spacedumpling.worktimemanager.presentation.activity

import android.app.Application
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import hu.bme.spacedumpling.worktimemanager.R
import hu.bme.spacedumpling.worktimemanager.modules.viewModelModules
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WorkTimeManagerMainActivity : AppCompatActivity(
    R.layout.activity_main
) {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (savedInstanceState == null) {
            setUpNavigation()
        }
    }

    private val navController: NavController by lazy { findNavController(R.id.fragment_navigator) }

    private fun setUpNavigation() {
        bottomNavigation.setupWithNavController(navController)
    }
}