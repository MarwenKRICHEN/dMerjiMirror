package com.example.dmerjimirror.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DetailViewModel: ViewModel() {

    private val _isRefreshing = MutableLiveData<Boolean>().apply {
        this.value = false
    }

    val isRefreshing: LiveData<Boolean> = _isRefreshing

    protected fun setIsRefreshing(isRefreshing: Boolean) {
        _isRefreshing.value = isRefreshing
    }

}