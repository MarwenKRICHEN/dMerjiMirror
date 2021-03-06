package com.example.dmerjimirror.ui.details.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentAddTodoElementBinding
import com.example.dmerjimirror.dialog.RoundedBottomSheetDialogFragment
import com.example.dmerjimirror.library.extension.setHour
import com.example.dmerjimirror.library.extension.setMinute
import com.example.dmerjimirror.library.model.response.TodoElement
import com.example.dmerjimirror.listener.TodoElementListener
import com.example.dmerjimirror.library.utils.MaterialTextInput
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddTodoElementFragment : RoundedBottomSheetDialogFragment() {

    private var _binding: FragmentAddTodoElementBinding? = null
    private var elementListener: TodoElementListener? = null
    private var todoElement: TodoElement? = null
    private var position: Int? = null
    override var state: Int = BottomSheetBehavior.STATE_COLLAPSED
    private var simpleDateFormat: SimpleDateFormat =
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

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
        setUpViews(view)
    }


    private fun setUpViews(view: View) {
        MaterialTextInput.setupClearErrors(view)
        if (todoElement != null) {
            binding.name.editText?.setText(todoElement?.name)
            binding.todoDeadLine.editText?.setText(todoElement?.deadline?.let {
                simpleDateFormat.format(
                    it
                )
            })
        }
        binding.header.headerTitle.text = context?.getString(R.string.todo_add_title) ?: ""
        binding.header.cancelButton.setOnClickListener {
            dismissAllowingStateLoss()
        }
        binding.header.saveButton.text = if (todoElement == null)
            context?.getString(R.string.global_add) ?: ""
        else
            context?.getString(R.string.global_update)

        binding.todoDeadLine.editText?.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setTheme(R.style.Theme_Date_Picker)
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            activity?.supportFragmentManager?.let { it1 -> datePicker.show(it1, "tag") }

            datePicker.addOnPositiveButtonClickListener {
                val date = Date(it)
                val timePicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setTitleText("Select deadline time")
                        .build()

                activity?.supportFragmentManager?.let { it1 -> timePicker.show(it1, "tag1") }
                timePicker.addOnPositiveButtonClickListener {
                    date.setHour(timePicker.hour)
                    date.setMinute(timePicker.minute)
                    binding.todoDeadLine.editText?.setText(simpleDateFormat.format(date))
                }

            }

        }

        binding.header.saveButton.setOnClickListener {
            val name = binding.name.editText?.text?.toString() ?: ""
            val dateString = binding.todoDeadLine.editText?.text?.toString() ?: ""
            var error = false
            if (name == "") {
                binding.name.error = getString(R.string.error_field_not_empty)
                error = true
            }
            if (dateString == "") {
                binding.todoDeadLine.error = getString(R.string.error_field_not_empty)
                error = true
            }
            if (!error) {
                try {
                    val date = simpleDateFormat?.parse(dateString)
                    if (date != null) {
                        if (todoElement == null)
                            elementListener?.addTodo(TodoElement(-1, name, date, false, 12))
                        else
                            if (position != null)
                                elementListener?.updateTodo(
                                    TodoElement(
                                        todoElement?.id ?: -1,
                                        name,
                                        date,
                                        todoElement?.done ?: false,
                                        12
                                    ),
                                    position!!
                                )
                    }
                } catch (e: Exception) {
                } finally {
                    dismissAllowingStateLoss()
                }
            }

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