package com.example.dmerjimirror.library.utils

import android.content.Context

class Metrics {
    companion object {
        fun getPxFromDpValue(dp: Float, context: Context): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
    }
}