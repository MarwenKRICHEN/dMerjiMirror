package com.example.dmerjimirror.library.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.ui.main.components.ComponentsFragmentDirections
import com.google.android.material.transition.MaterialSharedAxis

class DirectionAdapter(private val component: Component) {
    fun navigate(fragment: Fragment) {
        val action = when (component.name) {
            "Todo List" -> {
                ComponentsFragmentDirections.actionNavigationComponentsToTodoDetailFragment()
            }
            "News Feed" -> {
                ComponentsFragmentDirections.actionNavigationComponentsToNewsFeedDetailFragment()
            }
            "Weather" -> {
                ComponentsFragmentDirections.actionNavigationComponentsToWeatherFragment()
            }
            "Forecast" -> {
                ComponentsFragmentDirections.actionNavigationComponentsToWeatherForecastFragment()
            }
            "Clock" -> {
                ComponentsFragmentDirections.actionNavigationComponentsToClockFragment()
            }
            "Calendar" -> {
                ComponentsFragmentDirections.actionNavigationComponentsToCalendarFragment()
            }
            "Compliment" -> {
                ComponentsFragmentDirections.actionNavigationComponentsToComplimentFragment()
            }
            else -> null
        }
        if (action != null) {
            fragment.exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            fragment.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
            fragment.findNavController().navigate(action)
        }

    }
}
/*
 */