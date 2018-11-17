package com.piapps.flashcardpro.ui.controller

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.model.Label

/**
 * Created by abduaziz on 11/17/18 at 11:45 AM.
 */

class FilterLabelsController(var labels: ArrayList<Label>) : RecyclerView.Adapter<FilterLabelsController.LabelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        return LabelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_label, parent, false))
    }

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        holder.tv.text = labels[position].title
        if (labels[position].isSelected) {
            holder.tv.setBackgroundResource(R.drawable.label_bg_selected)
        } else {
            holder.tv.setBackgroundResource(R.drawable.label_bg)
        }
    }

    override fun getItemCount(): Int {
        return labels.size
    }

    inner class LabelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView = itemView.findViewById(R.id.tvLabel)

        init {
            tv.setOnClickListener {
                onLabelClickedListener?.onLabelClicked(labels[adapterPosition], adapterPosition)
            }
        }
    }

    var onLabelClickedListener: OnLabelClickedListener? = null

    interface OnLabelClickedListener {
        fun onLabelClicked(l: Label, pos: Int)
    }
}