package com.example.dmerjimirror.ui.details.compliment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.Calendar
import com.example.dmerjimirror.library.model.Component

class ComplimentViewModel: ViewModel() {

    private val _compliment = MutableLiveData<Component>().apply {
        this.value =
            Component(0, "Compliment", Component.Position.TOP_CENTER, true)
    }

    val compliment: LiveData<Component> = _compliment
}