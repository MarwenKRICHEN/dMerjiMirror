package com.example.dmerjimirror.ui.main.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.response.UserResponse

class UserResponseViewModel: ViewModel() {

    private val _userResponse = MutableLiveData<UserResponse?>().apply {
        this.value = null
    }

    val userResponse: LiveData<UserResponse?> = _userResponse

    fun setUserResponse(userResponse: UserResponse?) {
        _userResponse.value = userResponse
    }

}