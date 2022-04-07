package com.example.dmerjimirror.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.Component

class OverviewViewModel : ViewModel() {

    private val data: ArrayList<Component> = arrayListOf(
        Component(0,"Weather", Component.Position.TOP_RIGHT, true),
        Component(0,"Forecast", Component.Position.MIDDLE_RIGHT, true),
        Component(0,"Todo List", Component.Position.TOP_LEFT, true),
    )

    private val _components = MutableLiveData<ArrayList<Component>>().apply {
        value = data
    }

    val components: LiveData<ArrayList<Component>> = _components
}
