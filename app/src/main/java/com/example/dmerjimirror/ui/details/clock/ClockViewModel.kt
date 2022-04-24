package com.example.dmerjimirror.ui.details.clock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.controller.ClockController
import com.example.dmerjimirror.library.controller.WeatherController
import com.example.dmerjimirror.library.model.response.Clock
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.ui.details.DetailViewModel

class ClockViewModel : DetailViewModel() {

    private val _clock = MutableLiveData<Clock?>().apply {
        this.value = null
    }

    val clock: LiveData<Clock?> = _clock

    fun refresh(userId: Int) {
        setIsRefreshing(true)
        ClockController.get(userId) { clock, throwable ->
            setIsRefreshing(false)
            _clock.value = clock
        }
    }
}