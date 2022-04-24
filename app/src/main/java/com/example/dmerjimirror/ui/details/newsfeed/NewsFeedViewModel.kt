package com.example.dmerjimirror.ui.details.newsfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.controller.NewsFeedController
import com.example.dmerjimirror.library.controller.WeatherController
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.library.model.response.Feed
import com.example.dmerjimirror.library.model.response.NewsFeed
import com.example.dmerjimirror.ui.details.DetailViewModel
import kotlin.collections.ArrayList

class NewsFeedViewModel: DetailViewModel() {

    private val _component = MutableLiveData<NewsFeed?>().apply {
        this.value = null
    }

    val component: LiveData<NewsFeed?> = _component

    fun refresh(userId: Int) {
        setIsRefreshing(true)
        NewsFeedController.get(userId) { newsFeed, throwable ->
            setIsRefreshing(false)
            _component.value = newsFeed
        }
    }
}