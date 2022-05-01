package com.example.dmerjimirror.adapater

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.dmerjimirror.R
import com.example.dmerjimirror.library.extension.makeGone
import com.example.dmerjimirror.library.extension.makeVisible
import com.example.dmerjimirror.library.model.response.ProfileImage
import com.example.dmerjimirror.library.utils.Constants
import com.example.dmerjimirror.library.utils.ImageUtils
import com.example.dmerjimirror.listener.AdapterPositionListener
import java.lang.Exception

class ProfileImagesAdapter(
    var images: ArrayList<ProfileImage>,
    var listener: AdapterPositionListener,
    val context: Context
) : RecyclerView.Adapter<ProfileImagesAdapter.MyViewHolder>() {

    private val imagesFolder = Constants.BASE_URL + "uploads/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_user_image, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val image = try {
            images[position]
        } catch (e: Exception) {
            null
        }

        if (image == null) {
            // add image
            holder.profileAction?.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_add_24
                )
            )
            holder.profileAction?.setOnClickListener {
                listener.onAdd()
            }
            holder.progressBar?.makeGone()
        } else {
            // show image
            holder.progressBar?.makeVisible()
            holder.profileAction?.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_close_24
                )
            )
            holder.profileAction?.setOnClickListener {
                listener.onRemove(images.indexOf(image))
            }


            if (image.state == ProfileImage.State.COMPLETED) {
                holder.profileImage?.let {
                    ImageUtils.loadImageProfile(
                        context,
                        "$imagesFolder${image.name}",
                        it,
                        null
                    )
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return 9
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileImage: ImageView? = itemView.findViewById(R.id.profileImage)
        var profileAction: ImageView? = itemView.findViewById(R.id.profileAction)
        var progressBar: ProgressBar? = itemView.findViewById(R.id.progress)
    }

    fun addImage(image: ProfileImage) {
        images.add(image)
        notifyItemChanged(images.count() - 1)
    }

    fun uploadCompleted(name: String) {
        val position = images.indexOfFirst { it.name == name }
        images[position].state = ProfileImage.State.COMPLETED
        notifyItemChanged(position)
    }

    fun removeImage(position: Int) {
        images.removeAt(position)
        notifyItemRemoved(position)
    }
}