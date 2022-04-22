package com.example.dmerjimirror.dialog

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.dmerjimirror.R
import com.example.dmerjimirror.listener.DialogButtonsListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MaterialDialogsCreator() {
    companion object {
        fun createConfirmDelete(
            title: String?,
            message: String?,
            activity: Activity?,
            listener: DialogButtonsListener,
        ) {
            if (activity != null) {
                MaterialAlertDialogBuilder(activity, R.style.ConfirmAlert)
                    .setTitle(title)
                    .setNegativeButton(activity.resources.getString(R.string.global_cancel)) { dialog, which ->
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
                        listener.onNegativeClick()
                    }
                    .setPositiveButton(activity.resources.getString(R.string.global_delete)) { dialog, which ->
                        listener.onPositiveClick()
                    }

                    .setMessage(message)
                    .show()
            }

        }

        fun createYesOrNo(
            buttonNo: String?,
            buttonYes: String?,
            title: String?,
            activity: Activity?,
            listener: DialogButtonsListener
        ) {
            if (activity != null) {
                MaterialAlertDialogBuilder(activity, R.style.ConfirmAlert)
                    .setTitle(title)
                    .setNegativeButton(buttonNo) { dialog, which ->
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
                        listener.onNegativeClick()
                    }
                    .setPositiveButton(buttonYes) { dialog, which ->
                        listener.onPositiveClick()
                    }

                    .show()
            }

        }

        fun createYesOrNoWithDescription(
            buttonNo: String?,
            buttonYes: String?,
            title: String?,
            description: String?,
            activity: Activity?,
            listener: DialogButtonsListener
        ) {
            if (activity != null) {
                MaterialAlertDialogBuilder(activity, R.style.ConfirmAlert)
                    .setTitle(title)
                    .setMessage(description)
                    .setNegativeButton(buttonNo) { dialog, which ->
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
                        listener.onNegativeClick()
                    }
                    .setPositiveButton(buttonYes) { dialog, which ->
                        listener.onPositiveClick()
                    }

                    .show()
            }

        }

        fun createErrorDialog(
            title: String?,
            message: String?,
            activity: Activity?
        ) {
            if (activity != null) {
                MaterialAlertDialogBuilder(activity, R.style.Error_Alert)
                    .setTitle(title)
                    .setPositiveButton(activity.resources.getString(R.string.global_ok)) { dialog, which ->

                    }

                    .setMessage(message)
                    .show()
            }
        }

        fun createCustomViewDialog(
            viewId: Int,
            cancelable: Boolean,
            activity: Activity
        ): AlertDialog {
            val dialogView = activity.layoutInflater.inflate(viewId, null)
            if (dialogView.parent != null) {
                (dialogView.parent as ViewGroup).removeView(dialogView)
            }
            return MaterialAlertDialogBuilder(activity)
                .setView(dialogView)
                .setCancelable(cancelable)
                .create()

        }
    }


}