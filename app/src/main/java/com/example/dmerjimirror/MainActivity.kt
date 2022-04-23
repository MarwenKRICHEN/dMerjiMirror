package com.example.dmerjimirror

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dmerjimirror.databinding.ActivityMainBinding
import com.example.dmerjimirror.dialog.MaterialDialogsCreator
import com.example.dmerjimirror.library.model.response.UserResponse
import com.example.dmerjimirror.library.test.UserTest
import com.example.dmerjimirror.listener.DialogButtonsListener
import com.example.dmerjimirror.ui.main.view_model.UserResponseViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navView: BottomNavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val userResponseViewModel: UserResponseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val userResponse = intent.getSerializableExtra("UserResponse") as UserResponse?
        userResponseViewModel.setUserResponse(userResponse)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        navView = binding.contentMain.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_overview, R.id.navigation_components, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
    }

    fun createSignOutDialog() {
        MaterialDialogsCreator.createYesOrNoWithDescription(
            getString(R.string.global_cancel),
            getString(R.string.global_yes),
            getString(R.string.sign_out_title),
            getString(R.string.sign_out_description),
            this,
            object : DialogButtonsListener {
                override fun onPositiveClick() {
                    signOut()
                }

                override fun onNegativeClick() {}

            }
        )
    }

    fun signOut() {
        val editor = getSharedPreferences(
            getString(R.string.auth_shared_preferences),
            Context.MODE_PRIVATE
        ).edit()
        editor.remove(getString(R.string.auth_shared_preferences_access_token))
        editor.apply()
        val intent = Intent(this, AuthenticationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.title = getString(
            R.string.overview_welcome,
            userResponseViewModel.userResponse.value?.user?.fullname
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.signOut -> {
            createSignOutDialog()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}