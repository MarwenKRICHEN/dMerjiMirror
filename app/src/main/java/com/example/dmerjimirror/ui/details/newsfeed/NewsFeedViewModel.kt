package com.example.dmerjimirror.ui.details.newsfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.*
import java.util.*
import kotlin.collections.ArrayList

class NewsFeedViewModel: ViewModel() {
    private val myNewsFeed = NewsFeed(0, "Todo List", Component.Position.TOP_CENTER, true, false)

    private val myFeedList = arrayListOf(
        Feed(0, "BBC", ""),
        Feed(0, "France 24", ""),
        Feed(0, "New York Times", ""),
        Feed(0, "Al jazira", ""),
    )

    private val _component = MutableLiveData<NewsFeed>().apply {
        this.value = myNewsFeed
    }

    val component: LiveData<NewsFeed> = _component

    private val _feedList = MutableLiveData<ArrayList<Feed>>().apply {
        this.value = myFeedList
    }

    val feedList: LiveData<ArrayList<Feed>> = _feedList
}