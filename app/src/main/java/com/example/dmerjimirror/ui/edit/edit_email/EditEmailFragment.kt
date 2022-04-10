package com.example.dmerjimirror.ui.edit.edit_email

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentChangeEmailBinding
import com.example.dmerjimirror.dialog.RoundedBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditEmailFragment : RoundedBottomSheetDialogFragment() {

    private var _binding: FragmentChangeEmailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }


    private fun setUpViews() {
        binding.header.headerTitle.text = context?.getString(R.string.settings_profile_change_email) ?: ""
        binding.header.cancelButton.setOnClickListener {
            dismissAllowingStateLoss()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        // save goals
    }


    companion object {

        fun newInstance(): EditEmailFragment {
            val fragment = EditEmailFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}