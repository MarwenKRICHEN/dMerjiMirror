package com.example.dmerjimirror.ui.details.compliment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentCalendarBinding
import com.example.dmerjimirror.databinding.FragmentComplimentBinding
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.extension.makeVisible
import com.example.dmerjimirror.ui.details.calendar.CalendarViewModel
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

class ComplimentFragment : Fragment() {
    private var _binding: FragmentComplimentBinding? = null
    private lateinit var complimentViewModel: ComplimentViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        complimentViewModel =
            ViewModelProvider(this)[ComplimentViewModel::class.java]
        (activity as MainActivity?)?.navView?.makeGone(activity)

        _binding = FragmentComplimentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)

        complimentViewModel.compliment.observe(viewLifecycleOwner, Observer {
            binding.componentHeader.componentName.text = it.name
            binding.componentHeader.componentEnabledSwitch.isChecked = it.active
            binding.componentHeader.componentImage.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.compliment
                )
            )
        })


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}