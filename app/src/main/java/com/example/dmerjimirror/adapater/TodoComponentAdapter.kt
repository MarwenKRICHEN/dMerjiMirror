package com.example.dmerjimirror.adapater

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.R
import com.example.dmerjimirror.library.model.response.Feed
import com.example.dmerjimirror.library.model.response.Todo
import com.example.dmerjimirror.library.model.response.TodoElement
import com.example.dmerjimirror.listener.TodoElementListener
import com.example.dmerjimirror.ui.details.newsfeed.model.FeedItem
import com.example.dmerjimirror.ui.details.todo.model.ComponentHeader
import com.example.dmerjimirror.ui.details.todo.model.Items
import com.example.dmerjimirror.ui.details.todo.model.TodoItem
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TodoComponentAdapter(
    context: Context,
    activity: Activity,
    items: ArrayList<Items>,
    private val showAddTodoListener: View.OnClickListener,
    private val todoElementListener: TodoElementListener,
) :
    ComponentAdapter(context, activity, items) {

    private lateinit var componentHeaderViewHolder: ViewHolderComponentHeader

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        when (type) {
            Items.HEADER -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.layout_add_element_header, viewGroup, false)
                return ViewHolderAddTodoHeader(view)
            }
            Items.COMPONENT_HEADER -> {
                val view = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(R.layout.layout_todo_header, viewGroup, false)
                componentHeaderViewHolder = ViewHolderComponentHeader(view)
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
                    .inflate(R.layout.layout_add_element_header, viewGroup, false)
                return ViewHolderAddTodoHeader(view)
            }
        }
    }

    inner class ViewHolderComponentHeader(itemView: View) : ViewHolder(itemView) {
        private val name: TextView? = itemView.findViewById(R.id.component_name)
        private val image: ImageView? = itemView.findViewById(R.id.component_image)
        val enabledSwitch: SwitchMaterial? =
            itemView.findViewById(R.id.componentEnabledSwitch)
        val periodicityDropList: TextInputLayout? = itemView.findViewById(R.id.periodicity)

        override fun bindType(item: Items) {
            (item as ComponentHeader?)?.apply {
                name?.text = this.component.name
                enabledSwitch?.isChecked = this.component.active
                image?.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.todo_list))
                val items = Todo.listOfPeriodicity
                val adapter = ArrayAdapter(context, R.layout.drop_down_list_item, items)
                (periodicityDropList?.editText as? AutoCompleteTextView)?.let {
                    it.setAdapter(adapter)
                    it.setText(adapter.getItem((this.component as Todo?)?.periodicity ?: 0).toString(), false)
                }
            }
        }

    }

    inner class ViewHolderAddTodoHeader(itemView: View) : ViewHolder(itemView) {
        private val addTodoButton: Button? = itemView.findViewById(R.id.addElementButton)
        override fun bindType(item: Items) {
            addTodoButton?.setText(R.string.todo_add)
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

                doneCheckBox?.setOnCheckedChangeListener { compoundButton, b ->
                    this.todo.done = b
                }
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

    fun getTodoList(): ArrayList<TodoElement> {
        val elements = ArrayList<TodoElement>()
        for (item in items) {
            if (item is TodoItem) {
                elements.add(item.todo)
            }
        }
        return elements
    }

    fun getIsEnabled(): Boolean {
        return componentHeaderViewHolder.enabledSwitch?.isChecked ?: false
    }

    fun getPeriodicity(): Int {
        (componentHeaderViewHolder.periodicityDropList?.editText as? AutoCompleteTextView)?.let {
            return Todo.listOfPeriodicity.indexOf(it.text.toString())
        }
        return 0
    }



}