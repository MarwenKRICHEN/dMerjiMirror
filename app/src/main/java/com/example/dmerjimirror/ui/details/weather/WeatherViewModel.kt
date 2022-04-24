package com.example.dmerjimirror.ui.details.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.controller.WeatherController
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.library.model.response.Weather
import com.example.dmerjimirror.ui.details.DetailViewModel

class WeatherViewModel : DetailViewModel(){

    private val _weather = MutableLiveData<Weather?>().apply {
        this.value = null
    }

    val weather: LiveData<Weather?> = _weather

    fun refresh(userId: Int) {
        setIsRefreshing(true)
        WeatherController.get(userId) { weather, throwable ->
            setIsRefreshing(false)
            _weather.value = weather
        }
    }
}