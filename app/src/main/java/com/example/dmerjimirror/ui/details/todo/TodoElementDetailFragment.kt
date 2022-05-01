package com.example.dmerjimirror.ui.details.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.adapater.NewsFeedComponentAdapter
import com.example.dmerjimirror.adapater.TodoComponentAdapter
import com.example.dmerjimirror.databinding.FragmentComponentRecyclerDetailBinding
import com.example.dmerjimirror.library.controller.TodoListController
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.extension.makeVisible
import com.example.dmerjimirror.library.model.response.NewsFeed
import com.example.dmerjimirror.library.model.response.Todo
import com.example.dmerjimirror.library.model.response.TodoElement
import com.example.dmerjimirror.library.utils.SwipeToDeleteHelper
import com.example.dmerjimirror.listener.TodoElementListener
import com.example.dmerjimirror.ui.details.DetailFragment
import com.example.dmerjimirror.ui.details.newsfeed.model.FeedItem
import com.example.dmerjimirror.ui.details.todo.model.ComponentHeader
import com.example.dmerjimirror.ui.details.todo.model.Items
import com.example.dmerjimirror.ui.details.todo.model.TodoAddHeader
import com.example.dmerjimirror.ui.details.todo.model.TodoItem
import com.google.android.material.transition.MaterialSharedAxis


class TodoElementDetailFragment : DetailFragment(), View.OnClickListener, TodoElementListener {
    private var _binding: FragmentComponentRecyclerDetailBinding? = null
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var todoDetailViewModel: TodoDetailViewModel

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

        _binding = FragmentComponentRecyclerDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as MainActivity?)?.navView?.makeGone(activity)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)


        mRecyclerView = binding.recyclerView
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.layoutManager = LinearLayoutManager(this.context)

        val todo = todoDetailViewModel.component.value

        mRecyclerView.adapter = TodoComponentAdapter(
            requireContext(),
            requireActivity(),
            arrayListOf(
                ComponentHeader(todo ?: Todo()),
                TodoAddHeader(),
            ),
            this,
            this
        )
        reloadAdapter(todo)
        setupSwipeGesture()

        userResponseViewModel.userResponse.value?.let {
            todoDetailViewModel.refresh(it.user.id)
        }

        todoDetailViewModel.component.observe(viewLifecycleOwner, Observer {
            if (it == null && todoDetailViewModel.isRefreshing.value == false)
                showSnackbar(binding.root)
            reloadAdapter(it)
        })

        todoDetailViewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            toggleProgressViews(it, binding.recyclerView, binding.progress)
        })



        return root
    }

    private fun reloadAdapter(todo: Todo?) {
        val todoList = todo?.list
        val items = ArrayList<Items>()
        items.add(ComponentHeader(todo ?: Todo()))
        items.add(TodoAddHeader())
        for (todoElement in todoList ?: ArrayList()) {
            items.add(TodoItem(todoElement))
        }
        (mRecyclerView.adapter as? TodoComponentAdapter)?.reloadItems(items)
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

    private fun setupSwipeGesture() {
        val simpleCallback = SwipeToDeleteHelper(requireContext()) { position ->
            (mRecyclerView.adapter as TodoComponentAdapter?)?.deleteItem(
                position,
                R.string.todo_element_deleted
            ) {
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    override fun saveData(): Boolean {
        (mRecyclerView.adapter as? TodoComponentAdapter)?.let { adapter ->
            todoDetailViewModel.component.value?.let {
                TodoListController.update(
                    Todo(
                        it.id,
                        it.name,
                        it.position,
                        adapter.getIsEnabled(),
                        it.userid,
                        adapter.getPeriodicity(),
                        adapter.getTodoList()
                    )
                ) { _, _ -> }
            }
        }
        return true
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