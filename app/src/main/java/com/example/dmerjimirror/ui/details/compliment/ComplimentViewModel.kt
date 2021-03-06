package com.example.dmerjimirror.ui.details.compliment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.controller.ComplimentsController
import com.example.dmerjimirror.library.controller.WeatherController
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.ui.details.DetailViewModel

class ComplimentViewModel: DetailViewModel() {

    private val _compliment = MutableLiveData<Component>().apply {
        this.value =
            Component(0, "Compliment", Component.Position.TOP_CENTER, true, 0)
    }

    val compliment: LiveData<Component> = _compliment

    fun refresh(userId: Int) {
        setIsRefreshing(true)
        ComplimentsController.get(userId) { compliments, throwable ->
            setIsRefreshing(false)
            _compliment.value = compliments
        }
    }
}