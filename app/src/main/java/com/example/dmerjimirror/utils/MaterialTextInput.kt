package com.example.dmerjimirror.utils

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
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