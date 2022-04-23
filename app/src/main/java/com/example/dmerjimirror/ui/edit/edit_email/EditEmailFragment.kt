package com.example.dmerjimirror.ui.edit.edit_email

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentChangeEmailBinding
import com.example.dmerjimirror.dialog.RoundedBottomSheetDialogFragment
import com.example.dmerjimirror.library.controller.UserController
import com.example.dmerjimirror.library.model.request.user.update.UserUpdateEmail
import com.example.dmerjimirror.library.model.request.user.update.UserUpdatePassword
import com.example.dmerjimirror.library.utils.KProgressHUDUtility
import com.example.dmerjimirror.library.utils.MaterialTextInput
import com.example.dmerjimirror.library.utils.RegexValidator
import com.example.dmerjimirror.ui.main.view_model.UserResponseViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.net.SocketTimeoutException

class EditEmailFragment : RoundedBottomSheetDialogFragment() {

    private var _binding: FragmentChangeEmailBinding? = null
    private val userResponseViewModel: UserResponseViewModel by activityViewModels()

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
        MaterialTextInput.setupClearErrors(binding.root)

        binding.header.headerTitle.text =
            context?.getString(R.string.settings_profile_change_email) ?: ""
        binding.header.cancelButton.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.header.saveButton.setOnClickListener {
            if (!checkFields()) {
                val hud = KProgressHUDUtility.show(requireActivity())
                userResponseViewModel.userResponse.value?.let { userResponse ->
                    UserController.updateEmail(
                        UserUpdateEmail(
                            userResponse.user.id,
                            binding.password.editText?.text.toString(),
                            binding.email.editText?.text.toString()
                        )
                    ) { throwable, code ->
                        hud.dismiss()
                        if (code == 203) {
                            binding.email.error = getString(R.string.error_email_exists)
                        } else if (code == 400) {
                            binding.password.error = getString(R.string.error_password_invalid)
                        } else {
                            if (throwable != null) {
                                if (throwable is SocketTimeoutException)
                                    KProgressHUDUtility.showError(
                                        getString(R.string.error_timeout),
                                        requireActivity()
                                    )
                                else
                                    KProgressHUDUtility.showError(
                                        getString(R.string.error_unknown),
                                        requireActivity()
                                    )
                            } else {
                                dismissAllowingStateLoss()
                            }
                        }

                    }
                }

            }
        }

    }


    private fun checkFields(): Boolean {
        var error = false


        if (!RegexValidator.validateEmail(binding.email.editText?.text.toString())) {
            error = true
            binding.email.error = getString(R.string.error_email_invalid)
        }

        if ((binding.password.editText?.text ?: "").toString().length < 4) {
            error = true
            binding.password.error = getString(R.string.error_password_length)
        }

        return error
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