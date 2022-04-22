package com.example.dmerjimirror.ui.main.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.controller.UserController
import com.example.dmerjimirror.library.model.response.Component

class OverviewViewModel : ViewModel() {

    private val _isRefreshing = MutableLiveData<Boolean>().apply {
        this.value = false
    }

    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _newsFeed = MutableLiveData<Component?>().apply {
        this.value = null
    }

    val  newsFeed: LiveData<Component?> = _newsFeed

    private val _components = MutableLiveData<ArrayList<Component>>().apply {
        value = arrayListOf()
    }

    val components: LiveData<ArrayList<Component>> = _components

    fun refreshComponents(userid: Int) {
        _isRefreshing.value = true
        UserController.getComponents(userid) { components, throwable ->
            if (components != null) {

                val newComponents = arrayListOf<Component>()
                for (i in 0..5) {
                    val component = components.firstOrNull {
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
                val newsFeed = components.firstOrNull {
                    it.name == "News Feed" && it.active
                }
                _newsFeed.value = newsFeed
                _components.value = newComponents
            } else {
                _components.value = arrayListOf()
                _newsFeed. value = null
            }
            _isRefreshing.value = false

        }
    }
}
