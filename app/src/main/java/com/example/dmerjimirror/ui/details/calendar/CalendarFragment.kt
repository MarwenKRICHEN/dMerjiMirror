package com.example.dmerjimirror.ui.details.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentCalendarBinding
import com.example.dmerjimirror.library.controller.CalendarController
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.model.response.Calendar
import com.example.dmerjimirror.library.utils.MaterialTextInput
import com.example.dmerjimirror.ui.details.DetailFragment
import com.google.android.material.transition.MaterialSharedAxis

class CalendarFragment : DetailFragment() {
    private var _binding: FragmentCalendarBinding? = null
    private lateinit var calendarViewModel: CalendarViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        calendarViewModel =
            ViewModelProvider(this)[CalendarViewModel::class.java]
        (activity as MainActivity?)?.navView?.makeGone(activity)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        MaterialTextInput.setupClearErrors(binding.root)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)

        userResponseViewModel.userResponse.value?.let {
            calendarViewModel.refresh(it.user.id)
        }

        calendarViewModel.calendar.observe(viewLifecycleOwner, Observer {
            if (it == null && calendarViewModel.isRefreshing.value == false)
                showSnackbar(binding.root)
            binding.componentHeader.componentName.text = it?.name ?: ""
            binding.componentHeader.componentEnabledSwitch.isChecked = it?.active ?: false
            binding.componentHeader.componentImage.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.calendar
                )
            )
            val items = Calendar.countries
            val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_list_item, items)
            (binding.country.editText as? AutoCompleteTextView)?.let { it1 ->
                it1.setAdapter(adapter)
                it1.setText(it?.country ?: "")
            }
        })

        calendarViewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            toggleProgressViews(it, binding.contentMain, binding.progress)
        })

        return root
    }

    override fun saveData() {
        calendarViewModel.calendar.value?.let {
            if (!checkFields()) {
                CalendarController.update(
                    Calendar(
                        it.id,
                        it.name,
                        it.position,
                        binding.componentHeader.componentEnabledSwitch.isChecked,
                        it.userid,
                        binding.country.editText?.text.toString()
                    )
                ) { calendar, throwable ->
                    dataSaved()
                }
            }
        }
    }

    private fun checkFields(): Boolean {
        var error = false

        if ((binding.country.editText?.text ?: "").toString() == "") {
            error = true
            binding.country.error = getString(R.string.error_field_not_empty)
        }

        return error
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}