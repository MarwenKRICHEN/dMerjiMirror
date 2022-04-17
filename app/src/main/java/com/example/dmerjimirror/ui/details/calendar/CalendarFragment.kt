package com.example.dmerjimirror.ui.details.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentCalendarBinding
import com.example.dmerjimirror.databinding.FragmentClockBinding
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.model.Calendar
import com.example.dmerjimirror.library.model.Clock
import com.example.dmerjimirror.library.model.Component
import com.example.dmerjimirror.ui.details.clock.ClockViewModel
import com.google.android.material.transition.MaterialFadeThrough
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
            binding.country.editText?.setText(it.country)
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}