package com.example.dmerjimirror.library.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import java.io.ByteArrayOutputStream

class ImageUtils {
    companion object {
        fun downloadImage(context: Context?, d: Drawable): ByteArray {
            val bitmap = drawableToBitmap(d)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            return baos.toByteArray()
        }

        fun loadImage(
            context: Context,
            source: ByteArray?,
            target: ImageView,
            placeholder: Drawable?
        ) {
            if (source == null) {
                Glide.with(context)
                    .load(placeholder)
                    .into(target)
            } else {
                Glide.with(context)
                    .load(source)
                    .placeholder(placeholder)
                    .error(placeholder)
                    .into(target)
            }
        }

        fun loadImageProfile(
            context: Context,
            source: String?,
            target: ImageView,
            placeholder: Drawable?,
            listener: RequestListener<Drawable>? = null
        ) {
            loadImage(context, source, target, placeholder, 200, 200, listener)
        }


        fun loadImage(
            context: Context,
            source: String?,
            target: ImageView,
            placeholder: Drawable?,
            width: Int,
            height: Int,
            listener: RequestListener<Drawable>? = null
        ) {
            Glide.with(context)
                .load(source)
                .apply(RequestOptions().override(width, height))
                .placeholder(placeholder)
                .listener(listener)
                .into(target)
        }

        fun drawableToBitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
            val width = if (!drawable.bounds.isEmpty) drawable
                .bounds.width() else drawable.intrinsicWidth
            val height = if (!drawable.bounds.isEmpty) drawable
                .bounds.height() else drawable.intrinsicHeight
            val bitmap = Bitmap.createBitmap(
                if (width <= 0) 1 else width,
                if (height <= 0) 1 else height, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }

}
