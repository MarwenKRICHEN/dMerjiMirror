package com.example.dmerjimirror.adapater

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import com.example.dmerjimirror.R
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.model.response.Feed
import com.example.dmerjimirror.library.model.response.NewsFeed
import com.example.dmerjimirror.ui.details.newsfeed.model.FeedItem
import com.example.dmerjimirror.ui.details.todo.model.ComponentHeader
import com.example.dmerjimirror.ui.details.todo.model.Items
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlin.collections.ArrayList

class NewsFeedComponentAdapter(
    context: Context,
    activity: Activity,
    items: ArrayList<Items>,
    private val showAddFeedListener: View.OnClickListener,
) : ComponentAdapter(context, activity, items) {

    private lateinit var componentHeaderViewHolder: ViewHolderComponentHeader

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {

        when (type) {
            Items.HEADER -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.layout_add_element_header, viewGroup, false)
                return ViewHolderAddNewsFeedHeader(view)
            }
            Items.COMPONENT_HEADER -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.layout_news_feed_header, viewGroup, false)
                componentHeaderViewHolder = ViewHolderComponentHeader(view)
                return ViewHolderComponentHeader(view)
            }
            Items.FEEDS -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.layout_feed, viewGroup, false)
                return ViewHolderFeedItem(view)
            }

            else -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.layout_add_element_header, viewGroup, false)
                return ViewHolderAddNewsFeedHeader(view)
            }
        }
    }

    inner class ViewHolderComponentHeader(itemView: View) : ViewHolder(itemView) {
        private val name: TextView? = itemView.findViewById(R.id.component_name)
        private val image: ImageView? = itemView.findViewById(R.id.component_image)
        val enabledSwitch: SwitchMaterial? =
            itemView.findViewById(R.id.componentEnabledSwitch)
        val showDescriptionSwitch: SwitchMaterial? =
            itemView.findViewById(R.id.showDescriptionSwitch)

        override fun bindType(item: Items) {
            (item as ComponentHeader?)?.apply {
                name?.text = this.component.name
                enabledSwitch?.isChecked = this.component.active
                image?.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.news_feed
                    )
                )
                showDescriptionSwitch?.isChecked =
                    (this.component as NewsFeed?)?.showdescription ?: false
            }
        }

    }

    inner class ViewHolderAddNewsFeedHeader(itemView: View) : ViewHolder(itemView) {
        private val addFeedButton: Button? = itemView.findViewById(R.id.addElementButton)
        override fun bindType(item: Items) {
            addFeedButton?.setText(R.string.news_feed_add)
        }

        init {
            addFeedButton?.setOnClickListener(showAddFeedListener)
        }

    }

    inner class ViewHolderFeedItem(itemView: View) : ViewHolder(itemView) {
        private val category: TextView? = itemView.findViewById(R.id.feedCategory)
        private val country: TextView? = itemView.findViewById(R.id.feedCountry)
        override fun bindType(item: Items) {
            (item as FeedItem?)?.apply {
                val noFilters = context.getString(R.string.feed_no_filter)
                if (this.feed.category != "")
                    category?.text = context.getString(R.string.feed_category, this.feed.category)
                else
                    category?.text = context.getString(R.string.feed_category, noFilters)
                if (this.feed.country != "")
                    country?.text = context.getString(R.string.feed_country, this.feed.country)
                else
                    country?.text = context.getString(R.string.feed_country, noFilters)
            }
        }
    }

    fun addFeedItem(feedItem: FeedItem) {
        items.add(feedItem)
        notifyItemInserted(items.count() - 1)
    }

    fun addFeedItems(feedItems: ArrayList<FeedItem>) {
        items.addAll(feedItems)
        notifyDataSetChanged()
    }

    fun getFeeds(): ArrayList<Feed> {
        val feeds = ArrayList<Feed>()
        for (item in items) {
            if (item is FeedItem) {
                feeds.add(item.feed)
            }
        }
        return feeds
    }

    fun getIsEnabled(): Boolean {
        return componentHeaderViewHolder.enabledSwitch?.isChecked ?: false
    }

    fun getShowDescription(): Boolean {
        return componentHeaderViewHolder.showDescriptionSwitch?.isChecked ?: false
    }

}