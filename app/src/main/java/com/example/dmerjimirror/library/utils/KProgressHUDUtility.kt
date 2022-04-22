package com.example.dmerjimirror.library.utils

import android.app.Activity
import android.content.pm.ActivityInfo
import android.widget.ImageView
import com.example.dmerjimirror.R
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KProgressHUDUtility {
    companion object {

        fun showError(detailLabel: String, activity: Activity) {
            val imageView: ImageView = ImageView(activity)
            imageView.setBackgroundResource(R.drawable.ic_baseline_error_outline_24)
            val hud = KProgressHUD.create(activity)
                .setCustomView(imageView)
                .setLabel(activity.resources.getString(R.string.global_error))
                .setDetailsLabel(detailLabel)
                .setCancellable(true)
                .setDimAmount(0.5f)
                .show()
            CoroutineScope(Dispatchers.IO).launch {
                delay(3500L)
                hud.dismiss()
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
            }
        }

        suspend fun showSuccess(hud: KProgressHUD, activity: Activity) {
            val imageView = ImageView(activity)
            imageView.setBackgroundResource(R.drawable.check_icon_white)
            hud.setCustomView(imageView).setCancellable(true).setLabel(
                activity.resources?.getString(
                    R.string.global_done
                )
            )
            CoroutineScope(Dispatchers.IO).launch {
                delay(2000L)
                hud.dismiss()
            }
        }

        fun show(activity: Activity): KProgressHUD {
            return KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show()
        }

        fun show(detailLabel: String?, activity: Activity): KProgressHUD {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
            return KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(detailLabel)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show()
        }

    }
}