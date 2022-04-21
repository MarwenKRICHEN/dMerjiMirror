package com.example.dmerjimirror.ui.details.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentCalendarBinding
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.model.response.Calendar
import com.google.android.material.transition.MaterialSharedAxis

class CalendarFragment : Fragment() {
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

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)

        calendarViewModel.calendar.observe(viewLifecycleOwner, Observer {
            binding.componentHeader.componentName.text = it.name
            binding.componentHeader.componentEnabledSwitch.isChecked = it.active
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
                it1.setText(it.country)
            }
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}