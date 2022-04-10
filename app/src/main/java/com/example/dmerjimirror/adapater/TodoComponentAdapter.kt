package com.example.dmerjimirror.adapater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dmerjimirror.R
import com.example.dmerjimirror.library.model.TodoElement
import com.example.dmerjimirror.listener.TodoElementListener
import com.example.dmerjimirror.ui.details.todo.model.ComponentHeader
import com.example.dmerjimirror.ui.details.todo.model.Items
import com.example.dmerjimirror.ui.details.todo.model.TodoItem
import com.google.android.material.switchmaterial.SwitchMaterial
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TodoComponentAdapter(
    val context: Context,
    private val items: ArrayList<Items>,
    private val showAddTodoListener: View.OnClickListener,
    private val todoElementListener: TodoElementListener,
) :
    RecyclerView.Adapter<ViewHolder>() {

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
                val formatter = SimpleDateFormat("EEE, MMM d yyyy", Locale.getDefault())
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

}