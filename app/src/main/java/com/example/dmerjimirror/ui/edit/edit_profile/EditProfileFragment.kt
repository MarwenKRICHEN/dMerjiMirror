package com.example.dmerjimirror.ui.edit.edit_profile

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentEditProfileBinding
import com.example.dmerjimirror.dialog.RoundedBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class EditProfileFragment : RoundedBottomSheetDialogFragment() {

    private var _binding: FragmentEditProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }


    private fun setUpViews() {
        binding.header.headerTitle.text = context?.getString(R.string.settings_profile_edit) ?: ""
        binding.header.cancelButton.setOnClickListener {
            dismissAllowingStateLoss()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        // save goals
    }


    companion object {

        fun newInstance(
        ): EditProfileFragment {
            val fragment = EditProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}