package com.example.dmerjimirror.ui.details.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.controller.CalendarController
import com.example.dmerjimirror.library.controller.WeatherController
import com.example.dmerjimirror.library.model.response.Calendar
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.ui.details.DetailViewModel

class CalendarViewModel : DetailViewModel() {

    private val _calendar = MutableLiveData<Calendar?>().apply {
        this.value = null
    }

    val calendar: LiveData<Calendar?> = _calendar

    fun refresh(userId: Int) {
        setIsRefreshing(true)
        CalendarController.get(userId) { calendar, throwable ->
            setIsRefreshing(false)
            _calendar.value = calendar
        }
    }
}