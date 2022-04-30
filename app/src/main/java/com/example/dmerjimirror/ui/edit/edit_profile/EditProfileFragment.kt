package com.example.dmerjimirror.ui.edit.edit_profile

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dmerjimirror.R
import com.example.dmerjimirror.adapater.ProfileImagesAdapter
import com.example.dmerjimirror.databinding.FragmentEditProfileBinding
import com.example.dmerjimirror.dialog.RoundedBottomSheetDialogFragment
import com.example.dmerjimirror.library.controller.UserController
import com.example.dmerjimirror.library.model.request.user.update.UserUpdateProfile
import com.example.dmerjimirror.library.model.response.ProfileImage
import com.example.dmerjimirror.library.model.response.UserResponse
import com.example.dmerjimirror.listener.AdapterPositionListener
import com.example.dmerjimirror.ui.main.view_model.UserResponseViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*


class EditProfileFragment : RoundedBottomSheetDialogFragment(), AdapterPositionListener {

    private var _binding: FragmentEditProfileBinding? = null
    private val userResponseViewModel: UserResponseViewModel by activityViewModels()
    private var userUpdateProfile: UserUpdateProfile? = null
    private var resultLauncher: ActivityResultLauncher<Intent>? = null
    private var imageBody: MultipartBody.Part? = null

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




        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = ProfileImagesAdapter(
            arrayListOf(),
            this,
            requireContext()
        )


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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedImage = result.data?.data
                    selectedImage?.let {
                        (binding.recyclerView.adapter as? ProfileImagesAdapter)?.addImage(
                            ProfileImage(
                                UUID.randomUUID().toString(),
                                it
                            )
                        )
                        getImageBody(it)
                    }
                }
            }
    }


    override fun onAdd() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher?.launch(Intent.createChooser(intent, "Select Picture"))
    }

    override fun onRemove(position: Int) {
        (binding.recyclerView.adapter as? ProfileImagesAdapter)?.removeImage(position)
    }

    private fun getImageBody(uri: Uri) {
        lifecycleScope.launch {
            val stream = this@EditProfileFragment.activity?.contentResolver?.openInputStream(uri)
                ?: return@launch
            val request = RequestBody.create(
                MediaType.parse("image/*"),
                stream.readBytes()
            ) // read all bytes using kotlin extension
            imageBody = MultipartBody.Part.createFormData(
                "file",
                "test.jpg",
                request
            )
        }
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