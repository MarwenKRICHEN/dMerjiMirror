package com.example.dmerjimirror.listener

import com.example.dmerjimirror.library.model.Feed
import java.io.Serializable

interface FeedListener: Serializable {
    fun addFeed(feed: Feed)
}