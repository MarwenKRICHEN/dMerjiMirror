package com.example.dmerjimirror.adapater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.R
import com.example.dmerjimirror.library.model.response.Component
import com.google.android.material.card.MaterialCardView
import java.lang.Exception

class SmallComponentAdapter(
    private val context: Context,
    private val components: ArrayList<Component>,
) :
    RecyclerView.Adapter<SmallComponentAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SmallComponentAdapter.MyViewHolder {

        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_component_small, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SmallComponentAdapter.MyViewHolder, position: Int) {
        val component = components.firstOrNull {
            it.position.lowercase() == Component.getPositionStringFromIndex(position).lowercase()
                    && it.active
        }
        (holder.itemView as MaterialCardView?)?.isClickable = true
        if (component == null) {
            holder.componentName?.text = ""
            (holder.itemView as MaterialCardView?)?.cardElevation = 0f
        } else {
            holder.componentName?.text = component.name
            try {
                holder.componentImage?.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        context.resources.getIdentifier(component.getImageName(), "drawable", context.packageName)
                    )
                )
            } catch (e: Exception) {}

        }
    }

    override fun getItemCount(): Int {
        return 6
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var componentName: TextView? = itemView.findViewById(R.id.component_name)
        var componentImage: ImageView? = itemView.findViewById(R.id.component_image)

    }

    fun getComponents(): ArrayList<Component> {
        return components
    }


}