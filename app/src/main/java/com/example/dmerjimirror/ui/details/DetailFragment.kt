package com.example.dmerjimirror.ui.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dmerjimirror.R
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.extension.makeVisible
import com.example.dmerjimirror.ui.main.view_model.UserResponseViewModel
import com.google.android.material.snackbar.Snackbar

abstract class DetailFragment(): Fragment() {
    protected val userResponseViewModel: UserResponseViewModel by activityViewModels()

    // enables options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.component_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // handles clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> dataSaved()
        }
        return super.onOptionsItemSelected(item)
    }

    abstract fun saveData()

    private fun dataSaved() {
        saveData()
        activity?.onBackPressed()
    }

    protected fun showSnackbar(view: View) {
        Snackbar.make(view ,getString(R.string.error_unknown), Snackbar.LENGTH_LONG)
            .show()
    }

    protected fun toggleProgressViews(isRefreshing: Boolean, contentMain: View, progress: View) {
        if (isRefreshing) {
            contentMain.makeGone()
            progress.makeVisible()
        } else {
            contentMain.makeVisible()
            progress.makeGone()
        }
    }
}