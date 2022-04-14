/*
 * Copyright 2020 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmName("ViewUtils")
@file:Suppress("UNCHECKED_CAST")

package com.example.dmerjimirror.library.extension

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.view.*
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.core.view.*
import com.example.dmerjimirror.R


val View.hasLayoutParams: Boolean
    get() = (layoutParams != null)

var View.layoutParamsWidth: Int
    set(value) {
        setLayoutParamsSize(width = value)
    }
    get() = (layoutParams?.width ?: 0)

var View.layoutParamsHeight: Int
    set(value) {
        setLayoutParamsSize(height = value)
    }
    get() = (layoutParams?.height ?: 0)

@get:Px
var View.startMargin: Int
    set(@Px value) {
        setMargins(startMargin = value)
    }
    get() = marginStart

@get:Px
var View.topMargin: Int
    set(@Px value) {
        setMargins(topMargin = value)
    }
    get() = marginTop

@get:Px
var View.endMargin: Int
    set(@Px value) {
        setMargins(endMargin = value)
    }
    get() = marginEnd

@get:Px
var View.bottomMargin: Int
    set(@Px value) {
        setMargins(bottomMargin = value)
    }
    get() = marginBottom

@get:Px
val View.horizontalMargin: Int
    get() = (startMargin + endMargin)

@get:Px
val View.verticalMargin: Int
    get() = (topMargin + bottomMargin)

@get:Px
var View.startPadding: Int
    set(@Px value) {
        updatePadding(startPadding = value)
    }
    get() = paddingStart

@get:Px
var View.topPadding: Int
    set(@Px value) {
        updatePadding(topPadding = value)
    }
    get() = paddingTop

@get:Px
var View.endPadding: Int
    set(@Px value) {
        updatePadding(endPadding = value)
    }
    get() = paddingEnd

@get:Px
var View.bottomPadding: Int
    set(@Px value) {
        updatePadding(bottomPadding = value)
    }
    get() = paddingBottom

@get:Px
val View.horizontalPadding: Int
    get() = (startPadding + endPadding)

@get:Px
val View.verticalPadding: Int
    get() = (topPadding + bottomPadding)


fun View.setLayoutParamsSize(size: Int) {
    setLayoutParamsSize(width = size, height = size)
}


fun View.setLayoutParamsSize(
        width: Int = layoutParamsWidth,
        height: Int = layoutParamsHeight
) {
    if (!hasLayoutParams) return

    updateLayoutParams {
        this.width = width
        this.height = height
    }
}


fun View.setMargins(
        @Px startMargin: Int = this.startMargin,
        @Px topMargin: Int = this.topMargin,
        @Px endMargin: Int = this.endMargin,
        @Px bottomMargin: Int = this.bottomMargin
) {
    if (layoutParams !is ViewGroup.MarginLayoutParams) {
        return
    }

    updateLayoutParams<ViewGroup.MarginLayoutParams> {
        this.marginStart = startMargin
        this.topMargin = topMargin
        this.marginEnd = endMargin
        this.bottomMargin = bottomMargin
    }
}


fun View.setHorizontalMargin(@Px margin: Int) {
    setMargins(startMargin = margin, endMargin = margin)
}


fun View.setVerticalMargin(@Px margin: Int) {
    setMargins(topMargin = margin, bottomMargin = margin)
}


fun View.clearMargins() {
    setMargins(
            startMargin = 0,
            topMargin = 0,
            endMargin = 0,
            bottomMargin = 0
    )
}


fun View.clearHorizontalMargins() {
    setHorizontalMargin(0)
}


fun View.clearVerticalMargins() {
    setVerticalMargin(0)
}


fun View.clearStartMargin() {
    startMargin = 0
}


fun View.clearTopMargin() {
    topMargin = 0
}


fun View.clearEndMargin() {
    endMargin = 0
}


fun View.clearBottomMargin() {
    bottomMargin = 0
}


fun View.updatePadding(
        @Px startPadding: Int = this.paddingStart,
        @Px topPadding: Int = this.paddingTop,
        @Px endPadding: Int = this.paddingEnd,
        @Px bottomPadding: Int = this.paddingBottom
) {
    setPaddingRelative(startPadding, topPadding, endPadding, bottomPadding)
}


fun View.setHorizontalPadding(@Px padding: Int) {
    updatePadding(startPadding = padding, endPadding = padding)
}


fun View.setVerticalPadding(@Px padding: Int) {
    updatePadding(topPadding = padding, bottomPadding = padding)
}


fun View.clearPadding() {
    updatePadding(
            startPadding = 0,
            topPadding = 0,
            endPadding = 0,
            bottomPadding = 0
    )
}


fun View.clearHorizontalPadding() {
    setHorizontalPadding(0)
}


fun View.clearVerticalPadding() {
    setVerticalPadding(0)
}


fun View.clearStartPadding() {
    startPadding = 0
}


fun View.clearTopPadding() {
    topPadding = 0
}


fun View.clearEndPadding() {
    endPadding = 0
}


fun View.clearBottomPadding() {
    bottomPadding = 0
}


fun <T : ViewGroup.LayoutParams> View.toLayoutParams(): T {
    return (layoutParams as T)
}


fun View.getString(@StringRes stringId: Int): String {
    return context.getString(stringId)
}


fun View.getString(@StringRes stringId: Int, vararg args: Any): String {
    return context.getString(stringId, *args)
}


fun View.makeVisible() {
    isVisible = true
}


fun View.makeInvisible() {
    isInvisible = true
}


fun View.makeGone() {
    isGone = true
}


fun View.removeElevation() {
    elevation = 0f
}


fun View.onClick(action: (View) -> Unit) {
    setOnClickListener(action)
}


/**
 * Enables the view by setting its [View.isEnabled] property
 * to true and, optionally, changing its alpha.
 *
 * @param changeAlpha Whether to change the alpha of the view.
 * Default is false.
 * @param alpha The new alpha value for the view if [changeAlpha]
 * parameter is true. Default is 0.5f.
 * @param childrenToo Whether to enable children as well
 * Default is false.
 */
fun View.enable(
        changeAlpha: Boolean = false,
        alpha: Float = 1f,
        childrenToo: Boolean = false
) {
    if (!isEnabled) {
        isEnabled = true

        if (changeAlpha) {
            setAlpha(alpha)
        }

        if (childrenToo && (this is ViewGroup)) {
            for (child in children) {
                child.enable(changeAlpha, alpha, childrenToo)
            }
        }
    }
}


/**
 * Disables the view by setting its [View.isEnabled] property
 * to false and, optionally, changing its alpha.
 *
 * @param changeAlpha Whether to change the alpha of the view.
 * Default is false.
 * @param alpha The new alpha value for the view if [changeAlpha]
 * parameter is true. Default is 0.5f.
 * @param childrenToo Whether to disable children as well.
 * Default is false.
 */
fun View.disable(
        changeAlpha: Boolean = false,
        alpha: Float = 0.5f,
        childrenToo: Boolean = false
) {
    if (isEnabled) {
        isEnabled = false

        if (changeAlpha) {
            setAlpha(alpha)
        }

        if (childrenToo && (this is ViewGroup)) {
            for (child in children) {
                child.disable(changeAlpha, alpha, childrenToo)
            }
        }
    }
}


/**
 * Sets the horizontal and vertical scale of the view.
 *
 * @param scale The new scale
 */
fun View.setScale(scale: Float) {
    scaleX = scale
    scaleY = scale
}


fun View.postAction(action: () -> Unit) {
    post(action)
}


fun View.postActionDelayed(timeInMillis: Long, action: () -> Unit) {
    postDelayed(action, timeInMillis)
}


/**
 * Hides the system bars and makes the Activity "fullscreen". If this should be the default
 * state it should be called from [Activity.onWindowFocusChanged] if hasFocus is true.
 * It is also recommended to take care of cutout areas. The default behavior is that the app shows
 * in the cutout area in portrait mode if not in fullscreen mode. This can cause "jumping" if the
 * user swipes a system bar to show it. It is recommended to set [WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER],
 * call [showBelowCutout] from [Activity.onCreate]
 * (see [Android Developers article about cutouts](https://developer.android.com/guide/topics/display-cutout#never_render_content_in_the_display_cutout_area)).
 * @see showSystemUI
 * @see addSystemUIVisibilityListener
 */
fun Activity.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.let {
            // Default behavior is that if navigation bar is hidden, the system will "steal" touches
            // and show it again upon user's touch. We just want the user to be able to show the
            // navigation bar by swipe, touches are handled by custom code -> change system bar behavior.
            // Alternative to deprecated SYSTEM_UI_FLAG_IMMERSIVE.
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // make navigation bar translucent (alternative to deprecated
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            // - do this already in hideSystemUI() so that the bar
            // is translucent if user swipes it up
            window.navigationBarColor = getColor(R.color.black)
            // Finally, hide the system bars, alternative to View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            // and SYSTEM_UI_FLAG_FULLSCREEN.
            it.hide(WindowInsets.Type.systemBars())
        }
    } else {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
                // Do not let system steal touches for showing the navigation bar
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        // Keep the app content behind the bars even if user swipes them up
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        // make navbar translucent - do this already in hideSystemUI() so that the bar
        // is translucent if user swipes it up
        @Suppress("DEPRECATION")
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }
}

/**
 * Shows the system bars and returns back from fullscreen.
 * @see hideSystemUI
 * @see addSystemUIVisibilityListener
 */
fun Activity.showSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // show app content in fullscreen, i. e. behind the bars when they are shown (alternative to
        // deprecated View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.setDecorFitsSystemWindows(true)
        // finally, show the system bars
        window.insetsController?.show(WindowInsets.Type.systemBars())
    } else {
        // Shows the system bars by removing all the flags
        // except for the ones that make the content appear under the system bars.
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}

fun Activity.isInLandScapeMode(): Boolean {
    val orientation = resources.configuration.orientation
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        return true
    }
    return false
}