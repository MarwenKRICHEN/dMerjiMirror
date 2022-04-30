package com.example.dmerjimirror.adapater

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.R
import com.example.dmerjimirror.library.model.response.ProfileImage
import com.example.dmerjimirror.listener.AdapterPositionListener
import java.lang.Exception

class ProfileImagesAdapter(
    var images: ArrayList<ProfileImage>,
    var listener: AdapterPositionListener,
    val context: Context
) : RecyclerView.Adapter<ProfileImagesAdapter.MyViewHolder>() {

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

        holder.profileImage?.setImageURI(image?.uri)

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
        } else {
            // show image
            holder.profileAction?.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_close_24
                )
            )
            holder.profileAction?.setOnClickListener {
                listener.onRemove(images.indexOf(image))
            }
        }
    }

    override fun getItemCount(): Int {
        return 9
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileImage: ImageView? = itemView.findViewById(R.id.profileImage)
        var profileAction: ImageView? = itemView.findViewById(R.id.profileAction)
    }

    fun addImage(image: ProfileImage) {
        images.add(image)
        notifyItemChanged(images.count() - 1)
    }

    fun removeImage(position: Int) {
        images.removeAt(position)
        notifyItemRemoved(position)
    }
}