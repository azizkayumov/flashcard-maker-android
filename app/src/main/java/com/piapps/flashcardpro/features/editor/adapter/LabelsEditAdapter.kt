package com.piapps.flashcardpro.features.editor.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.piapps.flashcardpro.core.db.tables.LabelDb
import com.piapps.flashcardpro.features.editor.adapter.cells.LabelEditUI
import org.jetbrains.anko.AnkoContext

/**
 * Created by abduaziz on 2019-10-26 at 19:36.
 */

class LabelsEditAdapter(var list: ArrayList<LabelDb>, var labels: String = "") :
    RecyclerView.Adapter<LabelsEditAdapter.ViewHolder>() {

    fun remove(l: LabelDb) {
        val pos = list.indexOfFirst { it.id == l.id }
        if (pos < 0 || pos >= list.size) return
        list.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun labels(): String {
        var l = ""
        list.forEachIndexed { index, label ->
            if (label.selected) {
                if (index == list.size - 1)
                    l += label.title
                else
                    l += label.title + ","
            }
        }
        return l
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LabelEditUI().createView(AnkoContext.Companion.create(p0.context, p0)))
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.tv.text = list[pos].title
        list[pos].selected = labels.contains(list[pos].title)
        holder.checkBox.isChecked = list[pos].selected
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(LabelEditUI.tvId)
        val checkBox: CheckBox = itemView.findViewById(LabelEditUI.cbId)

        init {
            itemView.setOnClickListener {
                checkBox.toggle()
                list[adapterPosition].selected = checkBox.isChecked
            }
            itemView.setOnLongClickListener {
                onDeleteLabelListener?.onDeleteLabel(list[adapterPosition])
                true
            }
        }
    }

    var onDeleteLabelListener: OnDeleteLabelListener? = null

    interface OnDeleteLabelListener {
        fun onDeleteLabel(l: LabelDb)
    }
}