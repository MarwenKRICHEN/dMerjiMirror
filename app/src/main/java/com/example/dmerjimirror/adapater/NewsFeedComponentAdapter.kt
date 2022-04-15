package com.example.dmerjimirror.adapater

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import com.example.dmerjimirror.R
import com.example.dmerjimirror.library.model.Feed
import com.example.dmerjimirror.library.model.NewsFeed
import com.example.dmerjimirror.ui.details.newsfeed.model.FeedItem
import com.example.dmerjimirror.ui.details.todo.model.ComponentHeader
import com.example.dmerjimirror.ui.details.todo.model.Items
import com.example.dmerjimirror.ui.details.todo.model.TodoItem
import com.google.android.material.switchmaterial.SwitchMaterial
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsFeedComponentAdapter(
    context: Context,
    activity: Activity,
    items: ArrayList<Items>,
    private val showAddFeedListener: View.OnClickListener,
) : ComponentAdapter(context, activity, items) {
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
        private val enabledSwitch: SwitchMaterial? =
            itemView.findViewById(R.id.componentEnabledSwitch)
        private val showDescriptionSwitch: SwitchMaterial? =
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
                    (this.component as NewsFeed?)?.showDescription ?: false
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
        private val title: TextView? = itemView.findViewById(R.id.feedTitle)
        override fun bindType(item: Items) {
            (item as FeedItem?)?.apply {
                title?.text = this.feed.title
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

}