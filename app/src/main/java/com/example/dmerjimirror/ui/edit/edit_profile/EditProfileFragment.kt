package com.example.dmerjimirror.ui.edit.edit_profile

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.dmerjimirror.R
import com.example.dmerjimirror.databinding.FragmentEditProfileBinding
import com.example.dmerjimirror.dialog.RoundedBottomSheetDialogFragment
import com.example.dmerjimirror.library.controller.UserController
import com.example.dmerjimirror.library.model.request.user.update.UserUpdateProfile
import com.example.dmerjimirror.library.model.response.UserResponse
import com.example.dmerjimirror.ui.main.view_model.UserResponseViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class EditProfileFragment : RoundedBottomSheetDialogFragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val userResponseViewModel: UserResponseViewModel by activityViewModels()
    private var userUpdateProfile: UserUpdateProfile? = null

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
        userResponseViewModel.userResponse.value?.let { userResponse ->
            userUpdateProfile = UserUpdateProfile(
                userResponse.user.id,
                userResponse.user.fullname,
                userResponse.user.avatar
            )
        }
        setUpViews()
    }


    private fun setUpViews() {
        binding.header.headerTitle.text = getString(R.string.settings_profile_edit)
        binding.header.cancelButton.setOnClickListener {
            dismissAllowingStateLoss()
        }
        userResponseViewModel.userResponse.value?.let { userResponse ->
            binding.fullName.setText(userResponse.user.fullname)

            binding.fullName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    userUpdateProfile?.fullname = p0?.toString() ?: ""
                }

            })

            binding.header.saveButton.setOnClickListener {
                if (binding.fullName.text.toString() == "") {
                    binding.fullName.error = getString(R.string.error_field_not_empty)
                } else {
                    userUpdateProfile?.let { it1 ->
                        UserController.updateProfile(it1) { user, _, _ ->
                            if (user != null)
                                userResponseViewModel.setUserResponse(
                                    UserResponse(userResponse.token, user)
                                )
                        }
                    }
                    dismissAllowingStateLoss()
                }

            }

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