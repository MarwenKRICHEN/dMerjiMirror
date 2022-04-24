package com.example.dmerjimirror.ui.details.weather_forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.controller.WeatherController
import com.example.dmerjimirror.library.controller.WeatherForecastController
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.library.model.response.WeatherForecast
import com.example.dmerjimirror.ui.details.DetailViewModel


class WeatherForecastViewModel : DetailViewModel() {

    private val _weather = MutableLiveData<WeatherForecast?>().apply {
        this.value = null
    }

    val weather: LiveData<WeatherForecast?> = _weather


    fun refresh(userId: Int) {
        setIsRefreshing(true)
        WeatherForecastController.get(userId) { weather, throwable ->
            setIsRefreshing(false)
            _weather.value = weather
        }
    }
}