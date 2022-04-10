package com.example.dmerjimirror.ui.details.todo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.adapater.SmallComponentAdapter
import com.example.dmerjimirror.adapater.TodoComponentAdapter
import com.example.dmerjimirror.databinding.FragmentTodoDetailBinding
import com.example.dmerjimirror.extension.makeGone
import com.example.dmerjimirror.extension.makeVisible
import com.example.dmerjimirror.library.model.Component
import com.example.dmerjimirror.library.model.Todo
import com.example.dmerjimirror.library.model.TodoElement
import com.example.dmerjimirror.ui.details.todo.model.ComponentHeader
import com.example.dmerjimirror.ui.details.todo.model.TodoAddHeader
import com.example.dmerjimirror.ui.details.todo.model.TodoItem
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

class TodoDetailFragment: Fragment() {
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
            )
        )
        val todoItems = ArrayList<TodoItem>()
        for (todo in todoList ?: ArrayList()) {
            todoItems.add(TodoItem(todo))
        }
        (mRecyclerView.adapter as TodoComponentAdapter?)?.addTodoItems(todoItems)



        return root
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