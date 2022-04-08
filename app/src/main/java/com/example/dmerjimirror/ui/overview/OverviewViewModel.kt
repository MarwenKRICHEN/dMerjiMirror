package com.example.dmerjimirror.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.Component

class OverviewViewModel : ViewModel() {

    private val data: ArrayList<Component> = arrayListOf(
        Component(0, "Weather", Component.Position.TOP_RIGHT, true),
        Component(0, "Forecast", Component.Position.MIDDLE_RIGHT, true),
        Component(0, "Todo List", Component.Position.TOP_LEFT, true),
        Component(0, "Clock", Component.Position.TOP_CENTER, true),
    )

    private val _components = MutableLiveData<ArrayList<Component>>().apply {
        val newComponents = arrayListOf<Component>()
        for (i in 0..5) {
            val component = data.firstOrNull {
                it.position.lowercase() == Component.getPositionStringFromIndex(i).lowercase()
                        && it.active
            }
            if (component != null) {
                newComponents.add(component)
            } else {
                newComponents.add(
                    Component(
                        -1,
                        "",
                        Component.getPositionStringFromIndex(i),
                        false
                    )
                )
            }
        }
        value = newComponents
    }

    val components: LiveData<ArrayList<Component>> = _components
}
