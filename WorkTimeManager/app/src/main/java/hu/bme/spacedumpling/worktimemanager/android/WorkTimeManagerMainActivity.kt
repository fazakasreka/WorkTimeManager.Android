package hu.bme.spacedumpling.worktimemanager.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import hu.bme.spacedumpling.worktimemanager.R
import kotlinx.android.synthetic.main.activity_main.*

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