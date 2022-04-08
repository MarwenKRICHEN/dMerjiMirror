package com.example.dmerjimirror.adapater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dmerjimirror.R
import com.example.dmerjimirror.library.model.Component
import com.google.android.material.card.MaterialCardView

class LargeComponentAdapter(
    private val context: Context,
    private var components: ArrayList<Component>,
) :
    RecyclerView.Adapter<LargeComponentAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LargeComponentAdapter.MyViewHolder {

        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_component_large, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LargeComponentAdapter.MyViewHolder, position: Int) {
        val component = components[position]
        holder.componentName?.text = component.name
    }

    override fun getItemCount(): Int {
        return components.count()
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var componentName: TextView? = itemView.findViewById(R.id.component_name)
        var componentImage: ImageView? = itemView.findViewById(R.id.component_image)

    }

    fun getComponents(): ArrayList<Component> {
        return components
    }

    fun setComponents(components: ArrayList<Component>) {
        val componentsCount = this.components.count()
        this.components = components
        if (componentsCount == 0)
        // new Data
            notifyItemRangeInserted(0, componentsCount)
        else
            notifyDataSetChanged()
    }

}