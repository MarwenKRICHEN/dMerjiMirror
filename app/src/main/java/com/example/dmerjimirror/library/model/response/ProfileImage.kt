package com.example.dmerjimirror.library.model.response

import android.net.Uri

class ProfileImage(val name: String, var state: State?) {


    enum class State(value: Int) {
        LOADING(0),
        COMPLETED(1),
        FAILED(2),
    }
}

