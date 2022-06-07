package com.example.dmerjimirror.ui.details.compliment

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
import com.example.dmerjimirror.databinding.FragmentComplimentBinding
import com.example.dmerjimirror.library.controller.ComplimentsController
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.model.response.Clock
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.ui.details.DetailFragment
import com.google.android.material.transition.MaterialSharedAxis

class ComplimentFragment : DetailFragment() {
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

        userResponseViewModel.userResponse.value?.let {
            complimentViewModel.refresh(it.user.id)
        }


        complimentViewModel.compliment.observe(viewLifecycleOwner, Observer {
            if (it == null && complimentViewModel.isRefreshing.value == false)
                showSnackbar(binding.root)
            binding.componentHeader.componentName.text = it?.name ?: ""
            binding.componentHeader.componentEnabledSwitch.isChecked = it?.active ?: false
            binding.componentHeader.componentImage.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.compliments
                )
            )
        })

        complimentViewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            toggleProgressViews(it, binding.contentMain, binding.progress)
        })


        return root
    }

    override fun saveData() {
        complimentViewModel.compliment.value?.let {
            ComplimentsController.update(
                Component(
                    it.id,
                    it.name,
                    it.position,
                    binding.componentHeader.componentEnabledSwitch.isChecked,
                    it.userid
                )
            ) { component, throwable ->
                dataSaved()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}