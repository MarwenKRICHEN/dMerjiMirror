package com.example.dmerjimirror.ui.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentSettingsBinding
import com.example.dmerjimirror.ui.edit.edit_email.EditEmailFragment
import com.example.dmerjimirror.ui.edit.edit_password.EditPasswordFragment
import com.example.dmerjimirror.ui.edit.edit_profile.EditProfileFragment
import com.google.android.material.transition.MaterialFadeThrough

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        binding.timeFormatButtons.check(R.id.timeFormat24)

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
        }

        notificationsViewModel.text.observe(viewLifecycleOwner) {
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}