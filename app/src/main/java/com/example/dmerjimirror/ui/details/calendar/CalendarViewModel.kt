package com.example.dmerjimirror.ui.details.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.response.Calendar
import com.example.dmerjimirror.library.model.response.Component

class CalendarViewModel : ViewModel() {

    private val _calendar = MutableLiveData<Calendar>().apply {
        this.value =
            Calendar(0, "Clock", Component.Position.TOP_CENTER, true, "Tunisia")
    }

    val calendar: LiveData<Calendar> = _calendar
}