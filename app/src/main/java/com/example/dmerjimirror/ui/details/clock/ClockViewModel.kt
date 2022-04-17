package com.example.dmerjimirror.ui.details.clock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.Clock
import com.example.dmerjimirror.library.model.Component
import com.example.dmerjimirror.library.model.Weather

class ClockViewModel : ViewModel() {

    private val _clock = MutableLiveData<Clock>().apply {
        this.value =
            Clock(0, "Clock", Component.Position.TOP_CENTER, true, Clock.timeZones[5], true)
    }

    val clock: LiveData<Clock> = _clock
}