package com.example.dmerjimirror.ui.main.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentSettingsBinding
import com.example.dmerjimirror.library.controller.UserController
import com.example.dmerjimirror.library.model.request.user.update.UserUpdateTimeFormat
import com.example.dmerjimirror.library.model.request.user.update.UserUpdateUnit
import com.example.dmerjimirror.library.model.response.TimeFormat
import com.example.dmerjimirror.library.model.response.Unit
import com.example.dmerjimirror.ui.edit.edit_email.EditEmailFragment
import com.example.dmerjimirror.ui.edit.edit_password.EditPasswordFragment
import com.example.dmerjimirror.ui.edit.edit_profile.EditProfileFragment
import com.example.dmerjimirror.ui.main.view_model.UserResponseViewModel
import com.google.android.material.transition.MaterialFadeThrough

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val userResponseViewModel: UserResponseViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        userResponseViewModel.userResponse.value?.user?.let { user ->
            when (user.timeformat) {
                TimeFormat.TF12.value -> binding.timeFormatButtons.check(R.id.timeFormat12)
                TimeFormat.TF24.value -> binding.timeFormatButtons.check(R.id.timeFormat24)
            }

            when (user.unit) {
                Unit.METRIC.value -> binding.unitButtons.check(R.id.unitMetric)
                Unit.IMPERIAL.value -> binding.unitButtons.check(R.id.unitImperial)
            }


            binding.timeFormatButtons.addOnButtonCheckedListener { group, checkedId, isChecked ->
                var timeFormat: Int? = null
                if (checkedId == R.id.timeFormat12 && isChecked) {
                    timeFormat = TimeFormat.TF12.value
                }
                if (checkedId == R.id.timeFormat24 && isChecked) {
                    timeFormat = TimeFormat.TF24.value
                }
                timeFormat?.let {
                    UserController.updateTimeFormat(
                        UserUpdateTimeFormat(user.id, it)
                    ) { _, _, _ ->

                    }
                }
            }

            binding.unitButtons.addOnButtonCheckedListener { group, checkedId, isChecked ->
                var unit: Int? = null
                if (checkedId == R.id.unitImperial && isChecked) {
                    unit = Unit.IMPERIAL.value
                }
                if (checkedId == R.id.unitMetric && isChecked) {
                    unit = Unit.METRIC.value
                }
                unit?.let {
                    UserController.updateUnit(
                        UserUpdateUnit(user.id, it)
                    ) { _, _, _ ->

                    }
                }
            }

        }

        binding.editProfile.setOnClickListener {
            activity?.supportFragmentManager?.let {
                EditProfileFragment.newInstance().apply {
                    show(it, tag)
                }
            }
        }

        binding.changeEmail.setOnClickListener {
            activity?.supportFragmentManager?.let {
                EditEmailFragment.newInstance().apply {
                    show(it, tag)
                }
            }
        }

        binding.changePassword.setOnClickListener {
            activity?.supportFragmentManager?.let {
                EditPasswordFragment.newInstance().apply {
                    show(it, tag)
                }
            }
        }

        binding.signOut.setOnClickListener {
            (activity as? MainActivity?)?.createSignOutDialog()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}