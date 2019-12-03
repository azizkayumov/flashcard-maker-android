package com.piapps.flashcardpro.features.editor.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.piapps.flashcardpro.features.editor.adapter.cells.LabelUI
import org.jetbrains.anko.AnkoContext

/**
 * Created by abduaziz on 2019-10-26 at 19:36.
 */

class LabelsAdapter(val list: List<String>) : RecyclerView.Adapter<LabelsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LabelUI().createView(AnkoContext.Companion.create(p0.context, p0)))
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.tv.text = list[pos]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(LabelUI.tvId)
    }
}