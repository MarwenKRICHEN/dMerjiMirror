package com.example.dmerjimirror.library.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dmerjimirror.library.model.Component
import com.example.dmerjimirror.library.model.NewsFeed
import com.example.dmerjimirror.library.model.Todo
import com.example.dmerjimirror.ui.main.components.ComponentsFragment
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
        Component(0, "Weather", Component.Position.TOP_RIGHT, true),
        Component(0, "Clock", Component.Position.TOP_CENTER, true),
        Component(0, "Todo List", Component.Position.TOP_LEFT, true),
        Component(0, "Calendar", Component.Position.MIDDLE_RIGHT, true),
        Component(0, "News Feed", Component.Position.MIDDLE_RIGHT, true),
        Component(0, "Compliment", Component.Position.MIDDLE_RIGHT, true),
        Component(0, "Forecast", Component.Position.MIDDLE_RIGHT, true),
 */