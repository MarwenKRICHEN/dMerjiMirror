package com.example.dmerjimirror.ui.details.weather_forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.library.model.response.WeatherForecast


class WeatherForecastViewModel : ViewModel() {

    private val _weather = MutableLiveData<WeatherForecast>().apply {
        this.value = WeatherForecast(
            0,
            "Weather Forecast",
            Component.Position.TOP_CENTER,
            false,
            "Ariana, Tunisia",
            8,
            false
        )
    }

    val weather: LiveData<WeatherForecast> = _weather

}