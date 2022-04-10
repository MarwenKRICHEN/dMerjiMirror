package com.example.dmerjimirror.extension

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.dmerjimirror.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.makeVisible(activity: Activity?) {
    animation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.slide_in_bottom)
    isVisible = true
}


fun BottomNavigationView.makeGone(activity: Activity?) {
    animation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.slide_out_bottom)
    isGone = true
}