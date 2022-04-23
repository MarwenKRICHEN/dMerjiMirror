package com.example.dmerjimirror.ui.edit.edit_password

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentChangePasswordBinding
import com.example.dmerjimirror.databinding.FragmentEditProfileBinding
import com.example.dmerjimirror.dialog.RoundedBottomSheetDialogFragment
import com.example.dmerjimirror.library.controller.UserController
import com.example.dmerjimirror.library.model.request.user.update.UserUpdatePassword
import com.example.dmerjimirror.library.model.request.user.update.UserUpdateProfile
import com.example.dmerjimirror.library.utils.KProgressHUDUtility
import com.example.dmerjimirror.library.utils.MaterialTextInput
import com.example.dmerjimirror.ui.main.view_model.UserResponseViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditPasswordFragment : RoundedBottomSheetDialogFragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val userResponseViewModel: UserResponseViewModel by activityViewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }


    private fun setUpViews() {
        MaterialTextInput.setupClearErrors(binding.root)
        binding.header.headerTitle.text = getString(R.string.settings_profile_change_password)
        binding.header.cancelButton.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.header.saveButton.setOnClickListener {
            if (!checkFields()) {
                val hud = KProgressHUDUtility.show(requireActivity())
                userResponseViewModel.userResponse.value?.let { userResponse ->
                    UserController.updatePassword(
                        UserUpdatePassword(
                            userResponse.user.id,
                            binding.currentPassword.editText?.text.toString(),
                            binding.newPassword.editText?.text.toString()
                        )
                    ) { user, throwable, _ ->
                        if (user == null || throwable != null) {
                            hud.dismiss()
                            binding.currentPassword.error =
                                getString(R.string.error_password_invalid)
                        } else {
                            hud.dismiss()
                            dismissAllowingStateLoss()
                        }
                    }
                }

            }
        }

    }

    private fun checkFields(): Boolean {
        var error = false

        if ((binding.currentPassword.editText?.text ?: "").toString().length < 4) {
            error = true
            binding.currentPassword.error = getString(R.string.error_password_length)
        }

        if ((binding.newPassword.editText?.text ?: "").toString().length < 4) {
            error = true
            binding.newPassword.error = getString(R.string.error_password_length)
        }

        if ((binding.newPassword.editText?.text ?: "").toString() !=
            (binding.confirmPassword.editText?.text ?: "").toString()
        ) {
            error = true
            binding.confirmPassword.error = getString(R.string.error_confirm_password_match)
        }

        return error
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