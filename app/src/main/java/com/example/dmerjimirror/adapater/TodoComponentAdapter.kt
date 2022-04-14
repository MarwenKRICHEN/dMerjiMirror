package com.example.dmerjimirror.adapater

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dmerjimirror.R
import com.example.dmerjimirror.library.model.TodoElement
import com.example.dmerjimirror.listener.TodoElementListener
import com.example.dmerjimirror.ui.details.todo.model.ComponentHeader
import com.example.dmerjimirror.ui.details.todo.model.Items
import com.example.dmerjimirror.ui.details.todo.model.TodoItem
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TodoComponentAdapter(
    val context: Context,
    val activity: Activity,
    private val items: ArrayList<Items>,
    private val showAddTodoListener: View.OnClickListener,
    private val todoElementListener: TodoElementListener,
) :
    RecyclerView.Adapter<ViewHolder>() {

    private var lastRemovedItem: Items? = null
    private var lastRemovedItemIndex: Int? = null

    override fun getItemViewType(position: Int): Int {
        return items[position].listItemType()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        when (type) {
            Items.HEADER -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.layout_add_todo_header, viewGroup, false)
                return ViewHolderAddTodoHeader(view)
            }
            Items.COMPONENT_HEADER -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.layout_component_detail_header, viewGroup, false)
                return ViewHolderComponentHeader(view)
            }
            Items.TODO -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.layout_todo_element, viewGroup, false)
                return ViewHolderTodoItem(view)
            }

            else -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.layout_add_todo_header, viewGroup, false)
                return ViewHolderAddTodoHeader(view)
            }
        }
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

    inner class ViewHolderComponentHeader(itemView: View) : ViewHolder(itemView) {
        private val name: TextView? = itemView.findViewById(R.id.component_name)
        private val image: ImageView? = itemView.findViewById(R.id.component_image)
        private val enabledSwitch: SwitchMaterial? =
            itemView.findViewById(R.id.componentEnabledSwitch)

        override fun bindType(item: Items) {
            (item as ComponentHeader?)?.apply {
                name?.text = this.component.name
                enabledSwitch?.isChecked = this.component.active
                image?.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.todo_list))
            }
        }

    }

    inner class ViewHolderAddTodoHeader(itemView: View) : ViewHolder(itemView) {
        private val addTodoButton: Button? = itemView.findViewById(R.id.addTodoButton)
        override fun bindType(item: Items) {

        }

        init {
            addTodoButton?.setOnClickListener(showAddTodoListener)
        }

    }

    inner class ViewHolderTodoItem(itemView: View) : ViewHolder(itemView),
        View.OnLongClickListener {
        private val deadLine: TextView? = itemView.findViewById(R.id.todoDeadLine)
        private val name: TextView? = itemView.findViewById(R.id.todoName)
        private val doneCheckBox: CheckBox? = itemView.findViewById(R.id.todoDoneCheckBox)
        override fun bindType(item: Items) {
            (item as TodoItem?)?.apply {
                val formatter = SimpleDateFormat("EEE d MMM  HH:mm", Locale.getDefault())
                name?.text = this.todo.name
                deadLine?.text = formatter.format(this.todo.deadline)
                doneCheckBox?.isChecked = this.todo.done
            }
        }

        init {
            itemView.setOnLongClickListener(this)
        }

        override fun onLongClick(p0: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val todoElement = items[position] as TodoItem?
                if (todoElement != null)
                    todoElementListener.showUpdateTodo(todoElement.todo, position)
            }
            return false
        }

    }

    fun addTodoItem(todoItem: TodoItem) {
        items.add(todoItem)
        notifyItemInserted(items.count() - 1)
    }

    fun addTodoItems(todoItems: ArrayList<TodoItem>) {
        items.addAll(todoItems)
        notifyDataSetChanged()
    }

    fun updateTodoItem(todoItem: TodoItem, position: Int) {
        items[position] = todoItem
        notifyItemChanged(position)
    }

    fun deleteTodoItem(position: Int) {
        lastRemovedItemIndex = position
        try {
            lastRemovedItem = items[position]
        } catch (e: IndexOutOfBoundsException) {
            lastRemovedItem = null
            lastRemovedItemIndex = null
        }
        items.removeAt(position)
        notifyItemRemoved(position)
        showUndoSnackbar()
    }

    private fun showUndoSnackbar() {
        val view = activity.findViewById<View>(R.id.contentMain)
        val snackbar: Snackbar = Snackbar.make(
            view,
            context.getString(
                R.string.todo_element_deleted,
                (lastRemovedItem as TodoItem?)?.todo?.name
            ),
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction(R.string.global_undo) { undoDelete() }
        snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                // TODO: delete from DB
//                deleteItem((lastRemovedItem as TodoItem?)?.todo?.id)
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