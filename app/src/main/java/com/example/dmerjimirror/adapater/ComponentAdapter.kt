package com.example.dmerjimirror.adapater

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.R
import com.example.dmerjimirror.ui.details.newsfeed.model.FeedItem
import com.example.dmerjimirror.ui.details.todo.model.Items
import com.example.dmerjimirror.ui.details.todo.model.TodoItem
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

abstract class ComponentAdapter(
    val context: Context,
    val activity: Activity,
    protected val items: ArrayList<Items>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var lastRemovedItem: Items? = null
    private var lastRemovedItemIndex: Int? = null

    override fun getItemViewType(position: Int): Int {
        return items[position].listItemType()
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        val item = items[pos]
        (viewHolder as ViewHolder?)?.bindType(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindType(item: Items)
    }

    fun deleteItem(position: Int, message: Int, deleteItemFromDb: () -> Unit) {
        lastRemovedItemIndex = position
        try {
            lastRemovedItem = items[position]
        } catch (e: IndexOutOfBoundsException) {
            lastRemovedItem = null
            lastRemovedItemIndex = null
        }
        items.removeAt(position)
        notifyItemRemoved(position)
        showUndoSnackbar(message, deleteItemFromDb)
    }

    private fun showUndoSnackbar(message: Int, deleteItemFromDb: () -> Unit) {
        val view = activity.findViewById<View>(R.id.contentMain)
        val snackbar: Snackbar = Snackbar.make(
            view,
            context.getString(
                message,
                (lastRemovedItem as? TodoItem?)?.todo?.name,
                (lastRemovedItem as? FeedItem?)?.feed?.title,
            ),
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction(R.string.global_undo) { undoDelete() }
        snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                deleteItemFromDb()
            }
        })
        snackbar.show()
    }


    private fun undoDelete() {
        lastRemovedItemIndex?.let { index ->
            lastRemovedItem?.let { item ->
                items.add(index, item)
                notifyItemInserted(index)
                lastRemovedItemIndex = null
                lastRemovedItem = null
            }
        }

    }
}