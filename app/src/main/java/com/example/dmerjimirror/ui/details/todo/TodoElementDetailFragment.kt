package com.example.dmerjimirror.ui.details.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.adapater.TodoComponentAdapter
import com.example.dmerjimirror.databinding.FragmentTodoDetailBinding
import com.example.dmerjimirror.extension.makeGone
import com.example.dmerjimirror.extension.makeVisible
import com.example.dmerjimirror.library.model.Todo
import com.example.dmerjimirror.library.model.TodoElement
import com.example.dmerjimirror.listener.TodoElementListener
import com.example.dmerjimirror.ui.details.todo.model.ComponentHeader
import com.example.dmerjimirror.ui.details.todo.model.TodoAddHeader
import com.example.dmerjimirror.ui.details.todo.model.TodoItem
import com.google.android.material.transition.MaterialSharedAxis

class TodoElementDetailFragment : Fragment(), View.OnClickListener, TodoElementListener {
    private var _binding: FragmentTodoDetailBinding? = null
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var todoDetailViewModel: TodoDetailViewModel

    private var todo: Todo? = null
    private var todoList: ArrayList<TodoElement>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        todoDetailViewModel =
            ViewModelProvider(this)[TodoDetailViewModel::class.java]

        _binding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as MainActivity?)?.navView?.makeGone(activity)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)


        mRecyclerView = binding.recyclerView
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.layoutManager = LinearLayoutManager(this.context)

        todo = todoDetailViewModel.component.value
        todoList = todoDetailViewModel.todoList.value

        mRecyclerView.adapter = TodoComponentAdapter(
            requireContext(),
            arrayListOf(
                ComponentHeader(todo ?: Todo()),
                TodoAddHeader(),
            ),
            this,
            this
        )
        val todoItems = ArrayList<TodoItem>()
        for (todo in todoList ?: ArrayList()) {
            todoItems.add(TodoItem(todo))
        }
        (mRecyclerView.adapter as TodoComponentAdapter?)?.addTodoItems(todoItems)



        return root
    }

    // show add TodoElement
    override fun onClick(p0: View?) {
        activity?.supportFragmentManager?.let {
            AddTodoElementFragment.newInstance(this).apply {
                show(it, tag)
            }
        }
    }

    override fun addTodo(todoElement: TodoElement) {
        (mRecyclerView.adapter as TodoComponentAdapter?)?.addTodoItem(TodoItem(todoElement))
    }

    override fun showUpdateTodo(todoElement: TodoElement, position: Int) {
        activity?.supportFragmentManager?.let {
            AddTodoElementFragment.newInstance(this, todoElement, position).apply {
                show(it, tag)
            }
        }
    }

    override fun updateTodo(todoElement: TodoElement, position: Int) {
        (mRecyclerView.adapter as TodoComponentAdapter?)?.updateTodoItem(
            TodoItem(todoElement),
            position
        )
    }

    fun setupSwipeGesture() {
        val simpleCallback: ItemTouchHelper.SimpleCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }


                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
//                    mAdapter.deleteItem(position)
                }

            }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }


    override fun onPause() {
        (activity as MainActivity?)?.navView?.makeVisible()
        super.onPause()
    }

    override fun onDestroyView() {
        (activity as MainActivity?)?.navView?.makeVisible()
        super.onDestroyView()
        _binding = null
    }

}