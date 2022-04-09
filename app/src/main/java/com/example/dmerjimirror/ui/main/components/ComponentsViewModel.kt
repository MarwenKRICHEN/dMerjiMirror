package com.example.dmerjimirror.ui.main.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.Component

class ComponentsViewModel : ViewModel() {

    private val data: ArrayList<Component> = arrayListOf(
        Component(0, "Weather", Component.Position.TOP_RIGHT, true),
        Component(0, "Clock", Component.Position.TOP_CENTER, true),
        Component(0, "Todo List", Component.Position.TOP_LEFT, true),
        Component(0, "Calendar", Component.Position.MIDDLE_RIGHT, true),
        Component(0, "News Feed", Component.Position.MIDDLE_RIGHT, true),
        Component(0, "Compliment", Component.Position.MIDDLE_RIGHT, true),
        Component(0, "Weather Forecast", Component.Position.MIDDLE_RIGHT, true),
    )

    private val _components = MutableLiveData<ArrayList<Component>>().apply {
        value = data
    }

    val components: LiveData<ArrayList<Component>> = _components
}