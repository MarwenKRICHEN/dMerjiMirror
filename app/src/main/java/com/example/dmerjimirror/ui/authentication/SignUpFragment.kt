package com.example.dmerjimirror.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentSignInBinding
import com.example.dmerjimirror.databinding.FragmentSignUpBinding
import com.example.dmerjimirror.library.controller.UserController
import com.example.dmerjimirror.library.model.request.user.UserRegister
import com.example.dmerjimirror.library.utils.KProgressHUDUtility
import com.example.dmerjimirror.library.utils.MaterialTextInput
import com.example.dmerjimirror.library.utils.RegexValidator
import com.google.android.material.transition.MaterialSharedAxis
import okhttp3.Response
import java.net.SocketTimeoutException

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)

        MaterialTextInput.setupClearErrors(binding.root)

        binding.signUp.setOnClickListener {
            val error = checkFields()
            if (!error) {
                val hud = KProgressHUDUtility.show(requireActivity())
                UserController.register(
                    UserRegister(
                        binding.username.editText?.text.toString(),
                        binding.fullName.editText?.text.toString(),
                        binding.email.editText?.text.toString(),
                        binding.password.editText?.text.toString(),
                        binding.confirmPassword.editText?.text.toString(),
                        "Avatar"
                    )
                ) { message, throwable, code ->
                    hud.dismiss()
                    if (code == 203) {
                        binding.email.error = getString(R.string.error_email_username_exists)
                        binding.username.error = getString(R.string.error_email_username_exists)
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
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        }

        binding.signIn.setOnClickListener {
            activity?.onBackPressed()
        }

        return root
    }

    fun checkFields(): Boolean {
        var error = false
        if ((binding.fullName.editText?.text ?: "").toString() == "") {
            error = true
            binding.fullName.error = getString(R.string.error_field_not_empty)
        }

        if ((binding.username.editText?.text ?: "").toString().length < 4) {
            error = true
            binding.username.error = getString(R.string.error_username_length)
        }

        if (!RegexValidator.validateEmail(binding.email.editText?.text.toString())) {
            error = true
            binding.email.error = getString(R.string.error_email_invalid)
        }

        if ((binding.password.editText?.text ?: "").toString().length < 4) {
            error = true
            binding.password.error = getString(R.string.error_password_length)
        }

        if ((binding.password.editText?.text ?: "").toString() !=
            (binding.confirmPassword.editText?.text ?: "").toString()
        ) {
            error = true
            binding.confirmPassword.error = getString(R.string.error_confirm_password_match)
        }

        return error
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}