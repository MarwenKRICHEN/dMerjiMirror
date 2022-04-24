package com.example.dmerjimirror.ui.details.clock

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
import com.example.dmerjimirror.databinding.FragmentClockBinding
import com.example.dmerjimirror.library.controller.ClockController
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.model.response.Clock
import com.example.dmerjimirror.library.utils.MaterialTextInput
import com.example.dmerjimirror.ui.details.DetailFragment
import com.google.android.material.transition.MaterialSharedAxis

class ClockFragment : DetailFragment() {
    private var _binding: FragmentClockBinding? = null
    private lateinit var clockViewModel: ClockViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        clockViewModel =
            ViewModelProvider(this)[ClockViewModel::class.java]
        (activity as MainActivity?)?.navView?.makeGone(activity)

        _binding = FragmentClockBinding.inflate(inflater, container, false)
        val root: View = binding.root
        MaterialTextInput.setupClearErrors(binding.root)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)

        userResponseViewModel.userResponse.value?.let {
            clockViewModel.refresh(it.user.id)
        }

        clockViewModel.clock.observe(viewLifecycleOwner, Observer {
            if (it == null && clockViewModel.isRefreshing.value == false)
                showSnackbar(binding.root)
            binding.componentHeader.componentName.text = it?.name ?: ""
            binding.componentHeader.componentEnabledSwitch.isChecked = it?.active ?: false
            binding.componentHeader.componentImage.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.clock
                )
            )
            binding.digitalModeSwitch.isChecked = it?.isdigital ?: false
            val items = Clock.timeZones
            val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_list_item, items)
            (binding.timeZone.editText as? AutoCompleteTextView)?.let { it1 ->
                it1.setAdapter(adapter)
                it1.setText(it?.timezone ?: "")
            }
        })

        clockViewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            toggleProgressViews(it, binding.contentMain, binding.progress)
        })


        return root
    }

    override fun saveData(): Boolean {
        clockViewModel.clock.value?.let {
            if (!checkFields()) {
                ClockController.update(
                    Clock(
                        it.id,
                        it.name,
                        it.position,
                        binding.componentHeader.componentEnabledSwitch.isChecked,
                        it.userid,
                        binding.timeZone.editText?.text.toString(),
                        binding.digitalModeSwitch.isChecked
                    )
                ) { _, _ -> }
                return true
            }
        }
        return false
    }

    private fun checkFields(): Boolean {
        var error = false

        if ((binding.timeZone.editText?.text ?: "").toString() == "") {
            error = true
            binding.timeZone.error = getString(R.string.error_field_not_empty)
        }

        return error
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}