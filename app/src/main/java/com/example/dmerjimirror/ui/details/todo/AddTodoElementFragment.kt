package com.example.dmerjimirror.ui.details.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentAddTodoElementBinding
import com.example.dmerjimirror.dialog.RoundedBottomSheetDialogFragment
import com.example.dmerjimirror.library.model.TodoElement
import com.example.dmerjimirror.listener.TodoElementListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.*

class AddTodoElementFragment : RoundedBottomSheetDialogFragment() {

    private var _binding: FragmentAddTodoElementBinding? = null
    private var elementListener: TodoElementListener? = null
    private var todoElement: TodoElement? = null
    private var position: Int? = null
    override var state: Int = BottomSheetBehavior.STATE_COLLAPSED

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTodoElementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        elementListener = arguments?.getSerializable("listener") as TodoElementListener?
        todoElement = arguments?.getSerializable("element") as TodoElement?
        position = arguments?.getSerializable("position") as Int?
        setUpViews()
    }


    private fun setUpViews() {
        if (todoElement != null) {
            binding.name.editText?.setText(todoElement?.name)
        }
        binding.header.headerTitle.text = context?.getString(R.string.todo_add_title) ?: ""
        binding.header.cancelButton.setOnClickListener {
            dismissAllowingStateLoss()
        }
        binding.header.saveButton.text = context?.getString(R.string.global_add) ?: ""
        binding.header.saveButton.setOnClickListener {
            val name = binding.name.editText?.text?.toString() ?: ""
            if (todoElement == null)
                elementListener?.addTodo(TodoElement(-1, name, Date(), false))
            else
                if (position != null)
                    elementListener?.updateTodo(
                        TodoElement(
                            todoElement?.id ?: -1,
                            name,
                            Date(),
                            todoElement?.done ?: false
                        ),
                        position!!
                    )
            dismissAllowingStateLoss()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        // save goals
    }


    companion object {

        fun newInstance(
            elementListener: TodoElementListener,
            todoElement: TodoElement? = null,
            position: Int? = null
        ): AddTodoElementFragment {
            val fragment = AddTodoElementFragment()
            val args = Bundle()
            args.putSerializable("listener", elementListener)
            args.putSerializable("element", todoElement)
            args.putSerializable("position", position)
            fragment.arguments = args
            return fragment
        }
    }

}