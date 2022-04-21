package com.example.dmerjimirror.ui.details.newsfeed.model

import com.example.dmerjimirror.library.model.response.Feed
import com.example.dmerjimirror.ui.details.todo.model.Items

class FeedItem(val feed: Feed): Items {

    override fun listItemType(): Int {
        return Items.FEEDS
    }
}