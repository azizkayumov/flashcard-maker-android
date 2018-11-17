package com.piapps.flashcardpro.ui.controller

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.piapps.flashcardpro.R

/**
 * Created by abduaziz on 11/17/18 at 11:45 AM.
 */

class LabelsController(l: String) : RecyclerView.Adapter<LabelsController.LabelViewHolder>() {

    companion object {
        val DELIMITER = "___"
    }

    var labels = arrayListOf<String>()

    init {
        l.split(DELIMITER).forEach {
            if (it.isNotBlank())
                labels.add(it)
        }
    }

    fun addLabel(l: String) {
        if (l.isBlank() || labels.contains(l))
            return
        labels.add(l)
        notifyItemInserted(labels.size - 1)
    }

    fun labelsString(): String {
        var t = ""
        labels.forEachIndexed { index, s ->
            if (index == 0) {
                t = s
            } else {
                t += "$DELIMITER$s"
            }
        }
        return t
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        return LabelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_label, parent, false))
    }

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        holder.tv.text = labels[position]
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
        fun onLabelClicked(l: String, position: Int)
    }
}