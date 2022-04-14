package com.example.dmerjimirror.library.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

class MaterialTextInput {
    companion object {

        fun setupClearErrors(view: View) {
            if (view is TextInputLayout) {
                view.editText?.doOnTextChanged { text, start, before, count ->
                    view.error = null
                }
            }
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val innerView = view.getChildAt(i)
                    setupClearErrors(innerView)
                }
            }
        }
    }

}