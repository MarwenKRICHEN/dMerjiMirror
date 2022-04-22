package com.example.dmerjimirror.ui.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dmerjimirror.AuthenticationActivity
import com.example.dmerjimirror.MainActivity
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentSignInBinding
import com.example.dmerjimirror.library.controller.UserController
import com.example.dmerjimirror.library.model.request.user.UserLogin
import com.example.dmerjimirror.library.model.response.UserResponse
import com.example.dmerjimirror.library.utils.KProgressHUDUtility
import com.google.android.material.transition.MaterialSharedAxis
import java.net.SocketTimeoutException

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val root: View = binding.root

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)

        binding.createOne.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding.signInButton.setOnClickListener {
            val email = (binding.usernameEmail.editText?.text ?: "").toString()
            val password = (binding.password.editText?.text ?: "").toString()
            val hud = KProgressHUDUtility.show(requireActivity())
            UserController.login(UserLogin(email, password)) { userResponse, throwable ->
                if (throwable is SocketTimeoutException) {
                    hud.dismiss()
                    KProgressHUDUtility.showError(
                        getString(R.string.error_timeout),
                        requireActivity()
                    )
                } else {
                    if (throwable != null || userResponse == null) {
                        hud.dismiss()
                        KProgressHUDUtility.showError(
                            getString(R.string.error_invalid_credentials),
                            requireActivity()
                        )
                    } else {
                        hud.dismiss()
                        val editor = activity?.getSharedPreferences(
                            getString(R.string.auth_shared_preferences),
                            Context.MODE_PRIVATE
                        )?.edit()
                        editor?.putString(
                            getString(R.string.auth_shared_preferences_access_token),
                            userResponse.token
                        )
                        editor?.apply()
                        signIn(userResponse)
                    }

                }

            }
        }

        return root
    }

    private fun signIn(userResponse: UserResponse) {
        val intent = Intent(activity, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("UserResponse", userResponse)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}