package com.example.dmerjimirror.ui.details.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.Component
import com.example.dmerjimirror.library.model.Weather

class WeatherViewModel : ViewModel() {

    private val _weather = MutableLiveData<Weather>().apply {
        this.value = Weather(0, "Weather", Component.Position.TOP_CENTER, false, "Ariana, Tunisia")
    }

    val weather: LiveData<Weather> = _weather

}