package com.example.dmerjimirror.ui.edit.edit_password

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentChangePasswordBinding
import com.example.dmerjimirror.databinding.FragmentEditProfileBinding
import com.example.dmerjimirror.dialog.RoundedBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditPasswordFragment : RoundedBottomSheetDialogFragment() {

    private var _binding: FragmentChangePasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it1 ->
                val behaviour = BottomSheetBehavior.from(it1)
                setupFullHeight(it1)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.heightPixels
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = (displayMetrics.heightPixels * 0.9).toInt()
        bottomSheet.layoutParams = layoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }


    private fun setUpViews() {
        binding.header.headerTitle.text =
            context?.getString(R.string.settings_profile_change_password) ?: ""
        binding.header.cancelButton.setOnClickListener {
            dismissAllowingStateLoss()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        // save goals
    }


    companion object {

        fun newInstance(): EditPasswordFragment {
            val fragment = EditPasswordFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}